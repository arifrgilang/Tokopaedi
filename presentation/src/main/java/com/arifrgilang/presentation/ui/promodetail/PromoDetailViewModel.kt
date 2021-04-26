package com.arifrgilang.presentation.ui.promodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifrgilang.domain.interactor.promo.GetPromoUseCase
import com.arifrgilang.presentation.mapper.PromoDomainMapper
import com.arifrgilang.presentation.model.PromoUiModel
import com.arifrgilang.presentation.util.event.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by arifrgilang on 4/26/2021
 */
abstract class PromoDetailViewModel : ViewModel() {
    abstract val isLoading: LiveData<Boolean>
    abstract val isError: LiveData<Event<Unit>>
    abstract val promoData: LiveData<PromoUiModel>

    abstract fun getPromo(promoId: Int)
}

class PromoDetailViewModelImpl(
    private val getPromo: GetPromoUseCase,
    private val promoDomainMapper: PromoDomainMapper
) : PromoDetailViewModel() {
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

    private val _promoData = MediatorLiveData<PromoUiModel>()
    override val promoData: LiveData<PromoUiModel>
        get() = _promoData

    override fun getPromo(promoId: Int) {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            val rawData = getPromo.execute(promoId)
            val promoData = promoDomainMapper.mapDomainToUi(rawData)
            _promoData.postValue(promoData)
            _isLoading.value = false
        }
    }

}