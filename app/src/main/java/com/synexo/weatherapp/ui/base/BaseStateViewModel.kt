package com.synexo.weatherapp.ui.base

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<ViewModelEvent : Any, ViewEvent> : ViewModel() {

    private val _viewModelEvent = MutableStateFlow<Event<ViewModelEvent>?>(null)
    val viewModelEvent: StateFlow<Event<ViewModelEvent>?> = _viewModelEvent

    abstract fun onViewEvent(event: ViewEvent)

    protected fun setViewModelEvent(event: ViewModelEvent) {
        _viewModelEvent.value = Event(event)
    }
}

abstract class BaseStateViewModel<ViewState : Parcelable, ViewModelEvent : Any, ViewEvent>(
    state: SavedStateHandle
) : BaseViewModel<ViewModelEvent, ViewEvent>() {

    private companion object {
        const val VIEW_STATE = "VIEW_STATE"
    }

    private var _viewState = state.getMutableStateFlow(VIEW_STATE, defaultState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    protected fun getState() = viewState.value

    abstract fun defaultState(): ViewState

    protected fun modify(
        modifier: ViewState.() -> ViewState
    ) {
        val currentState = _viewState.value
        val newState = currentState.modifier()
        _viewState.value = newState
    }

    // Multi thread safe
    protected fun update(
        newState: ViewState.() -> ViewState
    ) {
        _viewState.update(newState)
    }
}

private class MutableSaveStateFlow<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    defaultValue: T
) {
    private val _state: MutableStateFlow<T> =
        MutableStateFlow(savedStateHandle.get<T>(key) ?: defaultValue)

    var value: T
        get() = _state.value
        set(value) {
            _state.value = value
            savedStateHandle[key] = value
        }

    fun update(
        modifier: T.() -> T
    ) {
        _state.update { _state.value.modifier() }
    }

    fun asStateFlow(): StateFlow<T> = _state
}

private fun <T> SavedStateHandle.getMutableStateFlow(
    key: String,
    initialValue: T
): MutableSaveStateFlow<T> =
    MutableSaveStateFlow(this, key, initialValue)

class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content

}