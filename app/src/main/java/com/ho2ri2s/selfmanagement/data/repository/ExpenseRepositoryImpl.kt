package com.ho2ri2s.selfmanagement.data.repository

import com.ho2ri2s.selfmanagement.data.api.ExpenseApiClient
import javax.inject.Inject

class ExpenseRepositoryImpl
    @Inject
    constructor(
        private val expenseApi: ExpenseApiClient,
        private val authRepository: AuthRepository,
    ) : ExpenseRepository {
        override suspend fun createIncome(
            year: Int,
            month: Int,
            amount: Int,
        ) {
            val userId = authRepository.getUserId()
            expenseApi.createIncome(userId, year, month, amount)
        }
    }