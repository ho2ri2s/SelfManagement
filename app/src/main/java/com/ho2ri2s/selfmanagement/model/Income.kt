package com.ho2ri2s.selfmanagement.model

import com.squareup.moshi.JsonClass

data class Income(
    val id: IncomeId,
    val amount: Int,
) {
    fun separatedAmount(): String {
        return amount.toString().reversed().chunked(3).joinToString(",").reversed()
    }
}

@JvmInline
value class IncomeId(val value: String)
