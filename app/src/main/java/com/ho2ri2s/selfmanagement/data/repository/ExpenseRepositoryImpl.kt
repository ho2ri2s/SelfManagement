package com.ho2ri2s.selfmanagement.data.repository

import com.ho2ri2s.selfmanagement.data.api.ExpenseApiClient
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseApi: ExpenseApiClient,
    private val authRepository: AuthRepository,
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

    }
}
