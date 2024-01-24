package com.ho2ri2s.selfmanagement.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.collect.ImmutableList
import com.ho2ri2s.selfmanagement.data.repository.ExpenseRepository
import com.ho2ri2s.selfmanagement.ext.buildUiState
import com.ho2ri2s.selfmanagement.model.Expense
import com.ho2ri2s.selfmanagement.model.Income
import com.ho2ri2s.selfmanagement.model.IncomeId
import com.ho2ri2s.selfmanagement.model.Outcome
import com.ho2ri2s.selfmanagement.model.OutcomeId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {
    private val mutableExpenseStateFlow: MutableStateFlow<Expense?> =
        MutableStateFlow(null)


    val needReloadStateFlow = expenseRepository.needReloadStateFlow
    val expenseUiState: StateFlow<ExpenseScreenUiState> =
        buildUiState(
            mutableExpenseStateFlow,
        ) { expense ->
            ExpenseScreenUiState(expense)
        }

    fun onShowScreen() {
        fetchExpense()
    }

    private fun fetchExpense() {
        viewModelScope.launch {
            runCatching {
                expenseRepository.getExpense(2024, 1)
            }.onSuccess {
                mutableExpenseStateFlow.value = it
            }.onFailure {
                // TODO: エラーハンドリング
            }
        }
    }

    fun onReload() {
        fetchExpense()
        expenseRepository.onReloaded()
    }
}
