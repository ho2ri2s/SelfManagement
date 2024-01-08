package com.ho2ri2s.selfmanagement.expense

import androidx.lifecycle.ViewModel
import com.google.common.collect.ImmutableList
import com.ho2ri2s.selfmanagement.ext.buildUiState
import com.ho2ri2s.selfmanagement.model.Expense
import com.ho2ri2s.selfmanagement.model.ExpenseId
import com.ho2ri2s.selfmanagement.model.Income
import com.ho2ri2s.selfmanagement.model.IncomeId
import com.ho2ri2s.selfmanagement.model.Outcome
import com.ho2ri2s.selfmanagement.model.OutcomeId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel
    @Inject
    constructor() : ViewModel() {
        private val mutableExpenseStateFlow: MutableStateFlow<Expense?> =
            MutableStateFlow(null)

        val uiState: StateFlow<ExpenseScreenUiModel> =
            buildUiState(
                mutableExpenseStateFlow,
            ) { expense ->
                ExpenseScreenUiModel(expense)
            }

        fun onShowScreen() {
            fetchExpense()
        }

        private fun fetchExpense() {
            val expense = dummyExpense()
            mutableExpenseStateFlow.value = expense
        }

        private fun dummyExpense(): Expense {
            val income =
                Income(
                    id = IncomeId("uuid"),
                    amount = 1000,
                )
            val outcomes =
                ImmutableList.builder<Outcome>().apply {
                    for (i in 0..10) {
                        add(
                            Outcome(
                                id = OutcomeId(i.toString()),
                                amount = 1000 * i,
                                title = "title$i",
                                day = i,
                            ),
                        )
                    }
                }.build()
            return Expense(
                id = ExpenseId("uuid"),
                year = 2024,
                month = 1,
                income = income,
                outcomes = outcomes,
            )
        }
    }
