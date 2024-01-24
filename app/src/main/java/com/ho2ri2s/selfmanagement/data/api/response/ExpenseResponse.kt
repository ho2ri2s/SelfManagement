package com.ho2ri2s.selfmanagement.data.api.response

data class ExpenseResponse(
    val year: Int,
    val month: Int,
    val income: IncomeResponse,
    val outcomes: List<OutcomeResponse>,
)
