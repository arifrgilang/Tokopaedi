package com.arifrgilang.presentation.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifrgilang.domain.interactor.item.GetItemWithCategoryUseCase
import com.arifrgilang.presentation.mapper.ItemDomainMapper
import com.arifrgilang.presentation.model.ItemUiModel
import com.arifrgilang.presentation.model.UserUiModel
import com.arifrgilang.presentation.util.event.Event
import com.arifrgilang.presentation.util.event.eventOf
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by arifrgilang on 4/24/2021
 */
abstract class DashboardViewModel : ViewModel() {
    abstract val userData: LiveData<UserUiModel>
    abstract val clothesData: LiveData<List<ItemUiModel>>
    abstract val isLoadingProfile: LiveData<Boolean>
    abstract val isLoadingClothes: LiveData<Boolean>
    abstract val isError: LiveData<Event<Unit>>

    abstract fun getUserData()
    abstract fun getClothesWithCategory(category: String)
}

class DashboardViewModelImpl(
    private val getItemWithCategoryUseCase: GetItemWithCategoryUseCase,
    private val itemDomainMapper: ItemDomainMapper
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
        Timber.d("Get clothes with category")
        viewModelScope.launch(errorHandler) {
            _isLoadingClothes.value = true
            getItemWithCategoryUseCase.execute(
                category
            ).catch { throwable ->
                Timber.e(throwable.toString())
                _isError.postValue(eventOf(Unit))
                _isLoadingClothes.value = false
            }.collect { clothes ->
                val clothesData = clothes.map { itemDomainMapper.mapDomainToUi(it) }
                Timber.d(clothesData.toString())
                _clothesData.postValue(clothesData)
                _isLoadingClothes.value = false
            }
        }
    }
}