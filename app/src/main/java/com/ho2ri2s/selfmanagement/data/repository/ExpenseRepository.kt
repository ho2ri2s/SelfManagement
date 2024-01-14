package com.ho2ri2s.selfmanagement.data.repository

interface ExpenseRepository {
    suspend fun createIncome(
        year: Int,
        month: Int,
        amount: Int,
    )
}
