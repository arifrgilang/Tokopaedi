package com.arifrgilang.presentation.ui.itemdetail

import androidx.lifecycle.*
import com.arifrgilang.domain.interactor.cart.PostCartUseCase
import com.arifrgilang.domain.interactor.item.GetItemUseCase
import com.arifrgilang.domain.interactor.item.PostItemsUseCase
import com.arifrgilang.domain.model.CartDomainModel
import com.arifrgilang.presentation.mapper.ItemDomainMapper
import com.arifrgilang.presentation.model.ItemUiModel
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
 * Created by arifrgilang on 4/26/2021
 */
abstract class ItemDetailViewModel : ViewModel() {
    abstract val isLoading: LiveData<Boolean>
    abstract val isError: LiveData<Event<Unit>>
    abstract val itemData: LiveData<ItemUiModel>
    abstract val isLoadingButton: LiveData<Boolean>
    abstract val isAdded: LiveData<Event<Boolean>>

    abstract fun getItemDetail(itemId: Int)
    abstract fun addToCart(item: ItemUiModel)
}

@FlowPreview
@ExperimentalCoroutinesApi
class ItemDetailViewModelImpl(
    private val getItemDetail: GetItemUseCase,
    private val itemDomainMapper: ItemDomainMapper,
    private val postCartUseCase: PostCartUseCase
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

    private val _isLoadingButton = MediatorLiveData<Boolean>()
    override val isLoadingButton: LiveData<Boolean>
        get() = _isLoadingButton

    private val isAddedChannel = ConflatedBroadcastChannel<Event<Boolean>>()
    override val isAdded: LiveData<Event<Boolean>> = isAddedChannel.asFlow().asLiveData()

    override fun getItemDetail(itemId: Int) {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            val rawData = getItemDetail.execute(itemId)
            val itemData = itemDomainMapper.mapDomainToUi(rawData)
            _itemData.postValue(itemData)
            _isLoading.value = false
        }
    }

    override fun addToCart(item: ItemUiModel) {
        viewModelScope.launch(errorHandler) {
            _isLoadingButton.value = true
            val user = FirebaseAuth.getInstance().currentUser
            postCartUseCase.execute(
                CartDomainModel(
                    0,
                    user?.email,
                    item.id,
                    item.itemName,
                    item.itemCategory,
                    item.itemVariant,
                    item.itemBrand,
                    item.itemPrice,
                    "USD",
                    1,
                    item.itemDesc,
                    item.itemPicUrl
                )
            )
            isAddedChannel.offer(eventOf(true))
            _isLoadingButton.value = false
        }
    }
}