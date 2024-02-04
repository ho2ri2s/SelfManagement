package com.ho2ri2s.selfmanagement.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ho2ri2s.selfmanagement.data.repository.ExpenseRepository
import com.ho2ri2s.selfmanagement.ext.buildUiState
import com.ho2ri2s.selfmanagement.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {
    private val mutableExpenseStateFlow: MutableStateFlow<Expense?> =
        MutableStateFlow(null)
    private val mutableCurrentDateStateFlow: MutableStateFlow<LocalDateTime> =
        MutableStateFlow(LocalDateTime.now())

    val needReloadStateFlow = expenseRepository.needReloadStateFlow
    val expenseUiState: StateFlow<ExpenseScreenUiState> =
        buildUiState(
            mutableExpenseStateFlow,
            mutableCurrentDateStateFlow,
        ) { expense, currentDate ->
            ExpenseScreenUiState(
                expense = expense,
                currentDate = currentDate,
            )
        }

    fun onShowScreen() {
        fetchExpense()
    }

    private fun fetchExpense() {
        viewModelScope.launch {
            runCatching {
                val year = mutableCurrentDateStateFlow.value.year
                val month = mutableCurrentDateStateFlow.value.monthValue
                expenseRepository.getExpense(year, month)
            }.onSuccess {
                mutableExpenseStateFlow.value = it
            }.onFailure {
                Timber.e(it, "Failed to fetch expense")
                // TODO: エラーハンドリング
            }
        }
    }

    fun onReload() {
        fetchExpense()
        expenseRepository.onReloaded()
    }

    fun onClickCalendarLeft() {
        mutableCurrentDateStateFlow.update { it.minusMonths(1) }
        fetchExpense()
    }

    fun onClickCalendarRight() {
        mutableCurrentDateStateFlow.update { it.plusMonths(1) }
        fetchExpense()
    }
}
