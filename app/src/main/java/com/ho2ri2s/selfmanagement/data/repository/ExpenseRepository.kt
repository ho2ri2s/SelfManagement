package com.ho2ri2s.selfmanagement.data.repository

import com.ho2ri2s.selfmanagement.model.Expense

interface ExpenseRepository {
    suspend fun createIncome(
        year: Int,
        month: Int,
        amount: Int,
    )

    suspend fun createOutcome(
        year: Int,
        month: Int,
        day: Int,
        title: String,
        amount: Int,
    )

    suspend fun getExpense(
        year: Int,
        month: Int,
    ): Expense
}
