package com.ho2ri2s.selfmanagement.data.repository

import com.ho2ri2s.selfmanagement.data.api.ExpenseApiClient
import com.ho2ri2s.selfmanagement.model.Expense
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseApi: ExpenseApiClient,
) : ExpenseRepository {
    override suspend fun createIncome(
        year: Int,
        month: Int,
        amount: Int,
    ) {
        expenseApi.createIncome(year, month, amount)
    }

    override suspend fun createOutcome(
        year: Int,
        month: Int,
        day: Int,
        title: String,
        amount: Int
    ) {
        expenseApi.createOutcome(year, month, day, title, amount)
    }

    override suspend fun getExpense(year: Int, month: Int): Expense {
        return expenseApi.getExpense(year, month)
    }
}
