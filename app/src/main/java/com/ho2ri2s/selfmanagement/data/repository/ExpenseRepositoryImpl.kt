package com.ho2ri2s.selfmanagement.data.repository

import com.ho2ri2s.selfmanagement.data.api.ExpenseApiClient
import com.ho2ri2s.selfmanagement.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseApi: ExpenseApiClient,
) : ExpenseRepository {

    private val mutableNeedReloadStateFlow = MutableStateFlow(false)
    override val needReloadStateFlow: StateFlow<Boolean>
        get() = mutableNeedReloadStateFlow.asStateFlow()

    override suspend fun createIncome(
        year: Int,
        month: Int,
        amount: Int,
    ) {
        runCatching {
            expenseApi.createIncome(year, month, amount)
        }.onSuccess {
            mutableNeedReloadStateFlow.update { true }
        }.onFailure {
            throw it
        }
    }

    override suspend fun createOutcome(
        year: Int,
        month: Int,
        day: Int,
        title: String,
        amount: Int
    ) {
        runCatching {
            expenseApi.createOutcome(year, month, day, title, amount)
        }.onSuccess {
            mutableNeedReloadStateFlow.update { true }
        }.onFailure {
            throw it
        }
    }

    override suspend fun getExpense(year: Int, month: Int): Expense {
        return expenseApi.getExpense(year, month)
    }

    override fun onReloaded() {
        mutableNeedReloadStateFlow.update { false }
    }
}
