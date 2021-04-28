package com.arifrgilang.presentation.ui

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifrgilang.domain.interactor.item.PostItemsUseCase
import com.arifrgilang.domain.interactor.promo.PostPromoUseCase
import com.arifrgilang.presentation.mapper.ItemDomainMapper
import com.arifrgilang.presentation.mapper.PromoDomainMapper
import com.arifrgilang.presentation.R
import com.arifrgilang.presentation.model.ItemUiModel
import com.arifrgilang.presentation.model.PromoUiModel
import com.arifrgilang.presentation.util.Constant.IS_DB_POPULATED
import com.arifrgilang.presentation.util.event.Event
import com.google.firebase.analytics.FirebaseAnalytics
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by arifrgilang on 4/24/2021
 */
abstract class MainViewModel : ViewModel() {
    abstract val isLoading: LiveData<Boolean>
    abstract val isError: LiveData<Event<Unit>>

    abstract fun populateDatabase(context: Context)
}

class MainViewModelImpl(
    private val itemDomainMapper: ItemDomainMapper,
    private val promoDomainMapper: PromoDomainMapper,
    private val postPromoUseCase: PostPromoUseCase,
    private val postItemsUseCase: PostItemsUseCase
) : MainViewModel() {

    private val _isLoading = MediatorLiveData<Boolean>()
    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isError = MediatorLiveData<Event<Unit>>()
    override val isError: LiveData<Event<Unit>>
        get() = _isError

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.toString())
        _isLoading.value = false
    }

    override fun populateDatabase(context: Context) {
        val isDbPopulated = Hawk.get<Boolean>(IS_DB_POPULATED)
        Timber.d("IS DB POP $isDbPopulated")
        if (isDbPopulated == null) {
            populateDb(context)
            Hawk.put(IS_DB_POPULATED, true)
        }
    }

    private fun populateDb(context: Context) {
        Timber.d("Populating database")
        val itemId = context.resources.getStringArray(R.array.item_id)
        val itemName = context.resources.getStringArray(R.array.item_name)
        val itemCategory = context.resources.getStringArray(R.array.item_category)
        val itemVariant = context.resources.getStringArray(R.array.item_variant)
        val itemBrand = context.resources.getStringArray(R.array.item_brand)
        val itemPrice = context.resources.getStringArray(R.array.item_price)
        val itemDesc = context.resources.getStringArray(R.array.item_desc)
        val itemPicUrl = context.resources.getStringArray(R.array.item_pic_url)

        val promoId = context.resources.getStringArray(R.array.promo_id)
        val promoName = context.resources.getStringArray(R.array.promo_name)
        val promoCreativeName = context.resources.getStringArray(R.array.promo_creative_name)
        val promoCreativeSlot = context.resources.getStringArray(R.array.promo_creative_slot)
        val promoLocationId = context.resources.getStringArray(R.array.promo_location_id)

        val itemList = mutableListOf<ItemUiModel>()

        for (index in itemId.indices) {
            itemList.add(
                ItemUiModel(
                    itemId[index].toInt(),
                    itemName[index],
                    itemCategory[index],
                    itemVariant[index],
                    itemBrand[index],
                    itemPrice[index].toLong(),
                    itemDesc[index],
                    itemPicUrl[index]
                )
            )
        }

        Timber.d(itemList.toString())

        val promoItems = mutableListOf(
            itemList.take(10),
            itemList.takeLast(10)
        )

        val promoList = mutableListOf<PromoUiModel>()

        for (index in promoId.indices) {
            promoList.add(
                PromoUiModel(
                    promoId[index].toInt(),
                    promoName[index],
                    promoCreativeName[index],
                    promoCreativeSlot[index],
                    promoLocationId[index],
                    promoItems[index]
                )
            )
        }

        postItemList(itemList)
        postPromoList(promoList)
    }

    private fun postItemList(itemList: List<ItemUiModel>) {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            postItemsUseCase.execute(
                itemList.map { itemDomainMapper.mapUiToDomain(it) }
            )
            _isLoading.value = false
        }
    }

    private fun postPromoList(promoList: List<PromoUiModel>) {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            postPromoUseCase.execute(
                promoList.map { promoDomainMapper.mapUiToDomain(it) }
            )
            _isLoading.value = false
        }
    }
}