package com.arifrgilang.presentation.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifrgilang.domain.interactor.history.GetHistoryWithEmailUseCase
import com.arifrgilang.presentation.mapper.HistoryDomainMapper
import com.arifrgilang.presentation.model.HistoryUiModel
import com.arifrgilang.presentation.util.event.Event
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by arifrgilang on 4/27/2021
 */
abstract class HistoryViewModel : ViewModel() {
    abstract val historyItems: LiveData<List<HistoryUiModel>>

    abstract val isLoading: LiveData<Boolean>
    abstract val isError: LiveData<Event<Unit>>

    abstract fun getHistoryItems()
}

class HistoryViewModelImpl(
    private val getHistoryWithEmail: GetHistoryWithEmailUseCase,
    private val historyDomainMapper: HistoryDomainMapper
) : HistoryViewModel() {
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.toString())
        _isLoading.value = false
    }

    private val _historyItems = MediatorLiveData<List<HistoryUiModel>>()
    override val historyItems: LiveData<List<HistoryUiModel>>
        get() = _historyItems

    private val _isLoading = MediatorLiveData<Boolean>()
    override val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isError = MediatorLiveData<Event<Unit>>()
    override val isError: LiveData<Event<Unit>>
        get() = _isError

    override fun getHistoryItems() {
        viewModelScope.launch(errorHandler) {
            _isLoading.value = true
            val user = FirebaseAuth.getInstance().currentUser
            getHistoryWithEmail.execute(user?.email!!).map { items ->
                items.map { historyDomainMapper.mapDomainToUi(it) }
            }.collect {
                _historyItems.postValue(it)
                _isLoading.value = false
            }
        }
    }
}