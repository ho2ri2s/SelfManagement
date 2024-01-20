package com.ho2ri2s.selfmanagement.data.api.response

import com.squareup.moshi.Json

data class IncomeResponse(
    @Json(name = "income_uuid") val id: String,
    val amount: Int,
)
