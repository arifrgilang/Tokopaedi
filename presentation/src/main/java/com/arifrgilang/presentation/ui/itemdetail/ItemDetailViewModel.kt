package com.arifrgilang.presentation.ui.itemdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifrgilang.domain.interactor.item.GetItemUseCase
import com.arifrgilang.presentation.mapper.ItemDomainMapper
import com.arifrgilang.presentation.model.ItemUiModel
import com.arifrgilang.presentation.util.event.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by arifrgilang on 4/26/2021
 */
abstract class ItemDetailViewModel : ViewModel() {
    abstract val isLoading: LiveData<Boolean>
    abstract val isError: LiveData<Event<Unit>>
    abstract val itemData: LiveData<ItemUiModel>

    abstract fun getItemDetail(itemId: Int)
}

class ItemDetailViewModelImpl(
    private val getItemDetail: GetItemUseCase,
    private val itemDomainMapper: ItemDomainMapper
): ItemDetailViewModel() {
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.toString())
        _isLoading.value = false
    }

    private val _isLoading = MediatorLiveData<Boolean>()
    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isError = MediatorLiveData<Event<Unit>>()
    override val isError: LiveData<Event<Unit>>
        get() = _isError

    private val _itemData = MediatorLiveData<ItemUiModel>()
    override val itemData: LiveData<ItemUiModel>
        get() = _itemData

    override fun getItemDetail(itemId: Int) {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            val rawData = getItemDetail.execute(itemId)
            val itemData = itemDomainMapper.mapDomainToUi(rawData)
            _itemData.postValue(itemData)
            _isLoading.value = false
        }
    }
}