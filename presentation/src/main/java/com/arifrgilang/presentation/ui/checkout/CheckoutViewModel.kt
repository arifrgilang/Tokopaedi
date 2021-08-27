package com.arifrgilang.presentation.ui.checkout

import androidx.lifecycle.*
import com.arifrgilang.domain.interactor.checkout.DelCheckoutWithIdUseCase
import com.arifrgilang.domain.interactor.checkout.GetCheckoutWithEmailUseCase
import com.arifrgilang.domain.interactor.history.PostHistoryUseCase
import com.arifrgilang.domain.model.HistoryDomainModel
import com.arifrgilang.presentation.mapper.CartDomainMapper
import com.arifrgilang.presentation.mapper.CheckoutDomainMapper
import com.arifrgilang.presentation.model.CheckoutUiModel
import com.arifrgilang.presentation.util.event.Event
import com.arifrgilang.presentation.util.event.eventOf
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by arifrgilang on 4/27/2021
 */
abstract class CheckoutViewModel : ViewModel() {
    abstract val checkoutItems: LiveData<List<CheckoutUiModel>>
    abstract val isItemCheckoutDeleted: LiveData<Event<Boolean>>
    abstract val isItemPaid: LiveData<Event<Boolean>>

    abstract val isLoading: LiveData<Boolean>
    abstract val isError: LiveData<Event<Unit>>

    abstract fun getCheckoutItems()
    abstract fun payItems(checkoutModel: CheckoutUiModel)
    abstract fun removeCheckout(checkoutId: String)
}

@FlowPreview
@ExperimentalCoroutinesApi
class CheckoutViewModelImpl(
    private val getCheckoutWithEmail: GetCheckoutWithEmailUseCase,
    private val checkoutDomainMapper: CheckoutDomainMapper,
    private val delCheckoutWithId: DelCheckoutWithIdUseCase,
    private val postHistory: PostHistoryUseCase,
    private val cartDomainMapper: CartDomainMapper
): CheckoutViewModel() {
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.toString())
        _isLoading.value = false
    }

    private val _checkoutItems = MediatorLiveData<List<CheckoutUiModel>>()
    override val checkoutItems: LiveData<List<CheckoutUiModel>>
        get() = _checkoutItems

    private val _isItemCheckoutDeleted = ConflatedBroadcastChannel<Event<Boolean>>()
    override val isItemCheckoutDeleted: LiveData<Event<Boolean>> =
        _isItemCheckoutDeleted.asFlow().asLiveData()

    private val _isItemPaid = ConflatedBroadcastChannel<Event<Boolean>>()
    override val isItemPaid: LiveData<Event<Boolean>> =
        _isItemPaid.asFlow().asLiveData()

    private val _isLoading = MediatorLiveData<Boolean>()
    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isError = MediatorLiveData<Event<Unit>>()
    override val isError: LiveData<Event<Unit>>
        get() = _isError

    override fun getCheckoutItems() {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            val user = FirebaseAuth.getInstance().currentUser
            getCheckoutWithEmail.execute(user?.email!!).map { items ->
                items.map { checkoutDomainMapper.mapDomainToUi(it) }
            }.collect {
                _checkoutItems.postValue(it)
                _isLoading.value = false
            }
        }
    }

    override fun payItems(checkoutModel: CheckoutUiModel) {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            val user = FirebaseAuth.getInstance().currentUser
            postHistory.execute(
                HistoryDomainModel(
                    0,
                    user?.email!!,
                    checkoutModel.items.map {
                        cartDomainMapper.mapUiToDomain(it)
                    }
                )
            )
            delCheckoutWithId.execute(checkoutModel.id)
            _isItemPaid.offer(eventOf(true))
            _isLoading.value = false
        }
    }

    override fun removeCheckout(checkoutId: String) {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            delCheckoutWithId.execute(checkoutId.toInt())
            _isItemCheckoutDeleted.offer(eventOf(true))
            _isLoading.value = false
        }
    }

}