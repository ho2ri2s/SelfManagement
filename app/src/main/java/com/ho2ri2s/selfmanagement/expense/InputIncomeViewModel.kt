package com.ho2ri2s.selfmanagement.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ho2ri2s.selfmanagement.data.repository.ExpenseRepository
import com.ho2ri2s.selfmanagement.ext.buildUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

// TODO: editは savedStateHandleで受け取る, Inputは月に一個
@HiltViewModel
class InputIncomeViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {
    private val mutableAmountStateFlow: MutableStateFlow<String> =
        MutableStateFlow("")

    val amountUiState: StateFlow<InputIncomeScreenUiState> =
        buildUiState(
            mutableAmountStateFlow,
        ) { amount ->
            InputIncomeScreenUiState(amount)
        }

    fun onChangeIncomeAmount(value: String) {
        mutableAmountStateFlow.value = value
    }

    fun onClickSave() {
        viewModelScope.launch {
            runCatching {
                expenseRepository.createIncome(
                    year = 2024,
                    month = 1,
                    amount = mutableAmountStateFlow.value.toIntOrNull() ?: 0,
                )
            }.onFailure {
                // TODO: snackbar
                Timber.e(it, "onClickSave")
            }
        }
    }
}
