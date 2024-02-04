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
import java.time.LocalDateTime
import javax.inject.Inject

// TODO: editは savedStateHandleで受け取る
@HiltViewModel
class InputIncomeViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {
    private val mutableAmountStateFlow: MutableStateFlow<String> =
        MutableStateFlow("")
    private val mutableCurrentDateStateFlow: MutableStateFlow<LocalDateTime> =
        MutableStateFlow(LocalDateTime.now())

    val amountUiState: StateFlow<InputIncomeScreenUiState> =
        buildUiState(
            mutableAmountStateFlow,
        ) { amount ->
            InputIncomeScreenUiState(amount)
        }

    fun setup(currentDateTime: LocalDateTime) {
        mutableCurrentDateStateFlow.value = currentDateTime
    }

    fun onChangeIncomeAmount(value: String) {
        mutableAmountStateFlow.value = value
    }

    fun onClickSave() {
        viewModelScope.launch {
            runCatching {
                val currentDate = mutableCurrentDateStateFlow.value
                expenseRepository.createIncome(
                    year = currentDate.year,
                    month = currentDate.monthValue,
                    amount = mutableAmountStateFlow.value.toIntOrNull() ?: 0,
                )
            }.onFailure {
                // TODO: snackbar
                Timber.e(it, "onClickSave")
            }
        }
    }
}
