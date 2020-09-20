package ru.padev.vkclient.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.padev.vkclient.core.presentation.events.ErrorEvent
import ru.padev.vkclient.core.presentation.events.ErrorResEvent
import ru.padev.vkclient.core.presentation.events.Event
import ru.padev.vkclient.core.presentation.events.ViewEvent

open class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    val eventLiveData: MutableLiveData<Event<ViewEvent>> by lazy {
        MutableLiveData<Event<ViewEvent>>()
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    protected open fun showError(message: String) {
        eventLiveData.postEvent(ErrorEvent(message))
    }

    protected open fun showError(messageRes: Int) {
        eventLiveData.postEvent(ErrorResEvent(messageRes))

    }

    fun Disposable.addToDisposables() {
        disposables.add(this)
    }

    fun MutableLiveData<Event<ViewEvent>>.postEvent(value: ViewEvent) {
        this.postValue(Event(value))
    }
}