package com.ho2ri2s.selfmanagement.model

import com.google.common.collect.ImmutableList

data class Expense(
    val id: ExpenseId,
    val year: Int,
    val month: Int,
    val income: Income,
    val outcomes: ImmutableList<Outcome>,
)

@JvmInline
value class ExpenseId(val value: String)
