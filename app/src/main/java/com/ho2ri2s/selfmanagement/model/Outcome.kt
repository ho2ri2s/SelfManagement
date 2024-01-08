package com.ho2ri2s.selfmanagement.model

data class Outcome(
    val id: OutcomeId,
    val amount: Int,
    val title: String,
    val day: Int,
) {
    fun separatedAmount(): String {
        return amount.toString().reversed().chunked(3).joinToString(",").reversed()
    }
}

@JvmInline
value class OutcomeId(val value: String)
