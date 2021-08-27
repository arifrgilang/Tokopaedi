package com.arifrgilang.presentation.ui.historydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifrgilang.domain.interactor.history.GetHistoryWithIdUseCase
import com.arifrgilang.presentation.mapper.HistoryDomainMapper
import com.arifrgilang.presentation.model.CartUiModel
import com.arifrgilang.presentation.util.event.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by arifrgilang on 4/27/2021
 */
abstract class HistoryDetailViewModel : ViewModel() {
    abstract val cartItems: LiveData<List<CartUiModel>>

    abstract val isLoading: LiveData<Boolean>
    abstract val isError: LiveData<Event<Unit>>

    abstract fun getHistoryItemsWithId(checkoutId: Int)
}

class HistoryDetailViewModelImpl(
    private val getHistoryWithId: GetHistoryWithIdUseCase,
    private val historyDomainMapper: HistoryDomainMapper
) : HistoryDetailViewModel() {
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.toString())
        _isLoading.value = false
    }

    private val _cartItems = MediatorLiveData<List<CartUiModel>>()
    override val cartItems: LiveData<List<CartUiModel>>
        get() = _cartItems

    private val _isLoading = MediatorLiveData<Boolean>()
    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isError = MediatorLiveData<Event<Unit>>()
    override val isError: LiveData<Event<Unit>>
        get() = _isError

    override fun getHistoryItemsWithId(checkoutId: Int) {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            getHistoryWithId.execute(checkoutId).map {
                historyDomainMapper.mapDomainToUi(it)
            }.collect { item ->
                _cartItems.postValue(item.items)
                _isLoading.value = false
            }
        }
    }

}