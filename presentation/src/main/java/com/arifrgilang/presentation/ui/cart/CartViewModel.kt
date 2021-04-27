package com.arifrgilang.presentation.ui.cart

import androidx.lifecycle.*
import com.arifrgilang.domain.interactor.cart.DelCartWithEmailUseCase
import com.arifrgilang.domain.interactor.cart.DelCartWithIdUseCase
import com.arifrgilang.domain.interactor.cart.GetCartWithEmail
import com.arifrgilang.domain.interactor.checkout.PostCheckoutUseCase
import com.arifrgilang.domain.model.CartDomainModel
import com.arifrgilang.domain.model.CheckoutDomainModel
import com.arifrgilang.presentation.mapper.CartDomainMapper
import com.arifrgilang.presentation.model.CartUiModel
import com.arifrgilang.presentation.util.event.Event
import com.arifrgilang.presentation.util.event.eventOf
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by arifrgilang on 4/27/2021
 */
abstract class CartViewModel : ViewModel() {
    abstract val cartItems: LiveData<List<CartUiModel>>
    abstract val isCheckoutClicked: LiveData<Event<Boolean>>
    abstract val isItemCartDeleted: LiveData<Event<Boolean>>

    abstract val isLoading: LiveData<Boolean>
    abstract val isError: LiveData<Event<Unit>>
    abstract val isLoadingButton: LiveData<Boolean>

    abstract fun getCartItems()
    abstract fun checkoutItems(items: List<CartUiModel>)
    abstract fun deleteItem(itemId: String)
}

@FlowPreview
@ExperimentalCoroutinesApi
class CartViewModelImpl(
    private val getCartWithEmail: GetCartWithEmail,
    private val cartDomainMapper: CartDomainMapper,
    private val delCartWithEmail: DelCartWithEmailUseCase,
    private val postCheckout: PostCheckoutUseCase,
    private val delCartWithId: DelCartWithIdUseCase
): CartViewModel() {
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.toString())
        _isLoading.value = false
        _isLoadingButton.value = false
    }

    private val _cartItems = MediatorLiveData<List<CartUiModel>>()
    override val cartItems: LiveData<List<CartUiModel>>
        get() = _cartItems

    private val isCheckoutClickedChannel = ConflatedBroadcastChannel<Event<Boolean>>()
    override val isCheckoutClicked: LiveData<Event<Boolean>> = isCheckoutClickedChannel.asFlow().asLiveData()

    private val isCartDeletedChannel = ConflatedBroadcastChannel<Event<Boolean>>()
    override val isItemCartDeleted: LiveData<Event<Boolean>> = isCartDeletedChannel.asFlow().asLiveData()

    private val _isLoading = MediatorLiveData<Boolean>()
    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isError = MediatorLiveData<Event<Unit>>()
    override val isError: LiveData<Event<Unit>>
        get() = _isError

    private val _isLoadingButton = MediatorLiveData<Boolean>()
    override val isLoadingButton: LiveData<Boolean>
        get() = _isLoadingButton

    override fun getCartItems() {
        Timber.d("GET CART ITEMS")
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            val user = FirebaseAuth.getInstance().currentUser
            val rawData = getCartWithEmail.execute(user?.email!!)
            val cartItemsData = rawData.map { model ->
                cartDomainMapper.mapDomainToUi(model)
            }
            _cartItems.postValue(cartItemsData)
            _isLoading.value = false
        }
    }

    override fun checkoutItems(items: List<CartUiModel>) {
        viewModelScope.launch(errorHandler) {
            _isLoadingButton.value = true
            val user = FirebaseAuth.getInstance().currentUser
            postCheckout.execute(
                CheckoutDomainModel(
                    0,
                    user?.email!!,
                    items.map { cartDomainMapper.mapUiToDomain(it) }
                )
            )
            delCartWithEmail.execute(user.email!!)
            isCheckoutClickedChannel.offer(eventOf(true))
            _isLoadingButton.value = false
        }
    }

    override fun deleteItem(itemId: String) {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            delCartWithId.execute(itemId.toInt())
            isCartDeletedChannel.offer(eventOf(true))
            _isLoading.value = false
        }
    }
}