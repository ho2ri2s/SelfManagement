package com.ho2ri2s.selfmanagement.model

import com.google.common.collect.ImmutableList

data class Expense(
    val year: Int,
    val month: Int,
    val income: Income,
    val outcomes: ImmutableList<Outcome>,
)
