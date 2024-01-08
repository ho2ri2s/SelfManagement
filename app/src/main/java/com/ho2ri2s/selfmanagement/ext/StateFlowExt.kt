package com.ho2ri2s.selfmanagement.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

fun <T1, R> ViewModel.buildUiState(
    stateFlow1: StateFlow<T1>,
    transform: (T1) -> R,
): StateFlow<R> {
    return stateFlow1.map { transform(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = transform(stateFlow1.value),
    )
}

fun <T1, T2, R> ViewModel.buildUiState(
    stateFlow1: StateFlow<T1>,
    stateFlow2: StateFlow<T2>,
    transform: (T1, T2) -> R,
): StateFlow<R> {
    return combine(
        flow = stateFlow1,
        flow2 = stateFlow2,
        transform = transform,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = transform(stateFlow1.value, stateFlow2.value),
    )
}

fun <T1, T2, T3, R> ViewModel.buildUiState(
    stateFlow1: StateFlow<T1>,
    stateFlow2: StateFlow<T2>,
    stateFlow3: StateFlow<T3>,
    transform: (T1, T2, T3) -> R,
): StateFlow<R> {
    return combine(
        flow = stateFlow1,
        flow2 = stateFlow2,
        flow3 = stateFlow3,
        transform = transform,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = transform(stateFlow1.value, stateFlow2.value, stateFlow3.value),
    )
}

fun <T1, T2, T3, T4, R> ViewModel.buildUiState(
    stateFlow1: StateFlow<T1>,
    stateFlow2: StateFlow<T2>,
    stateFlow3: StateFlow<T3>,
    stateFlow4: StateFlow<T4>,
    transform: (T1, T2, T3, T4) -> R,
): StateFlow<R> {
    return combine(
        flow = stateFlow1,
        flow2 = stateFlow2,
        flow3 = stateFlow3,
        flow4 = stateFlow4,
        transform = transform,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue =
            transform(
                stateFlow1.value,
                stateFlow2.value,
                stateFlow3.value,
                stateFlow4.value,
            ),
    )
}
