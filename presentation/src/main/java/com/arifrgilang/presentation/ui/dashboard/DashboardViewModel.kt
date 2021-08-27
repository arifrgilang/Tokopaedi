package com.arifrgilang.presentation.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifrgilang.domain.interactor.cart.GetCartWithEmail
import com.arifrgilang.domain.interactor.checkout.GetCheckoutWithEmailUseCase
import com.arifrgilang.domain.interactor.history.GetHistoryWithEmailUseCase
import com.arifrgilang.domain.interactor.item.GetItemWithCategoryUseCase
import com.arifrgilang.presentation.mapper.CartDomainMapper
import com.arifrgilang.presentation.mapper.CheckoutDomainMapper
import com.arifrgilang.presentation.mapper.HistoryDomainMapper
import com.arifrgilang.presentation.mapper.ItemDomainMapper
import com.arifrgilang.presentation.model.*
import com.arifrgilang.presentation.util.event.Event
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by arifrgilang on 4/24/2021
 */
abstract class DashboardViewModel : ViewModel() {
    abstract val userData: LiveData<UserUiModel>
    abstract val clothesData: LiveData<List<ItemUiModel>>
    abstract val cartCount: LiveData<Int>
    abstract val checkoutCount: LiveData<Int>
    abstract val historyCount: LiveData<Int>

    abstract val isLoadingProfile: LiveData<Boolean>
    abstract val isLoadingClothes: LiveData<Boolean>
    abstract val isError: LiveData<Event<Unit>>

    abstract fun getUserData()
    abstract fun getClothesWithCategory(category: String)
    abstract fun getCartWithEmail(email: String)
    abstract fun getCheckoutWithEmail(email: String)
    abstract fun getHistoryWithEmail(email: String)
}

class DashboardViewModelImpl(
    private val getItemWithCategoryUseCase: GetItemWithCategoryUseCase,
    private val itemDomainMapper: ItemDomainMapper,
    private val getCartWithEmail: GetCartWithEmail,
    private val cartDomainMapper: CartDomainMapper,
    private val getCheckoutWithEmail: GetCheckoutWithEmailUseCase,
    private val checkoutDomainMapper: CheckoutDomainMapper,
    private val getHistoryWithEmail: GetHistoryWithEmailUseCase,
    private val historyDomainMapper: HistoryDomainMapper
) : DashboardViewModel() {
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.toString())
        _isLoadingClothes.value = false
    }

    private val _userData = MediatorLiveData<UserUiModel>()
    override val userData: LiveData<UserUiModel>
        get() = _userData

    private val _clothesData = MediatorLiveData<List<ItemUiModel>>()
    override val clothesData: LiveData<List<ItemUiModel>>
        get() = _clothesData

    private val _cartCount = MediatorLiveData<Int>()
    override val cartCount: LiveData<Int>
        get() = _cartCount

    private val _checkoutCount = MediatorLiveData<Int>()
    override val checkoutCount: LiveData<Int>
        get() = _checkoutCount

    private val _historyCount = MediatorLiveData<Int>()
    override val historyCount: LiveData<Int>
        get() = _historyCount

    private val _isLoadingProfile = MediatorLiveData<Boolean>()
    override val isLoadingProfile: LiveData<Boolean>
        get() = _isLoadingProfile

    private val _isLoadingClothes = MediatorLiveData<Boolean>()
    override val isLoadingClothes: LiveData<Boolean>
        get() = _isLoadingClothes

    private val _isError = MediatorLiveData<Event<Unit>>()
    override val isError: LiveData<Event<Unit>>
        get() = _isError

    override fun getUserData() {
        _isLoadingProfile.value = true
        val user = FirebaseAuth.getInstance().currentUser
        user?.let{
            val userModel = UserUiModel(
                it.uid,
                it.displayName!!,
                it.email!!,
                it.photoUrl!!.toString()
            )
            Timber.d(userModel.toString())
            _userData.postValue(userModel)
            _isLoadingProfile.value = false
        }
    }

    override fun getClothesWithCategory(category: String) {
        Timber.d("Get clothes with category $category")
        viewModelScope.launch(errorHandler) {
            _isLoadingClothes.value = true
            getItemWithCategoryUseCase.execute(category).map { items ->
                items.map { itemDomainMapper.mapDomainToUi(it) }
            }.collect {
                _clothesData.postValue(it)
                _isLoadingClothes.value = false
            }
        }
    }

    override fun getCartWithEmail(email: String) {
        viewModelScope.launch(errorHandler) {
            _isLoadingProfile.value = true
            getCartWithEmail.execute(email).map { items ->
                items.map { cartDomainMapper.mapDomainToUi(it) }
            }.collect {
                _cartCount.postValue(it.size)
                _isLoadingProfile.value = false
            }
        }
    }

    override fun getCheckoutWithEmail(email: String) {
        viewModelScope.launch(errorHandler) {
            _isLoadingProfile.value = true
            getCheckoutWithEmail.execute(email).map { items ->
                items.map { checkoutDomainMapper.mapDomainToUi(it) }
            }.collect {
                _checkoutCount.postValue(it.size)
                _isLoadingProfile.value = false
            }
        }
    }

    override fun getHistoryWithEmail(email: String) {
        viewModelScope.launch(errorHandler) {
            _isLoadingProfile.value = true
            getHistoryWithEmail.execute(email).map { items ->
                items.map { historyDomainMapper.mapDomainToUi(it) }
            }.collect {
                _historyCount.postValue(it.size)
                _isLoadingProfile.value = false
            }
        }
    }
}