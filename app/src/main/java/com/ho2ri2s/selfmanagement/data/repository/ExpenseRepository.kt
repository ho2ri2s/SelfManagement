package com.ho2ri2s.selfmanagement.data.repository

interface ExpenseRepository {
    suspend fun createIncome(
        year: Int,
        month: Int,
        amount: Int,
    )

    /*
        "year": "int",
    "month": "int",
    "amount": "int", // 支出額
    "title": "string", // 支出タイトル
    "day": "int", // 支出日
     */
    suspend fun createOutcome(
        year: Int,
        month: Int,
        day: Int,
        title: String,
        amount: Int,
    )
}
