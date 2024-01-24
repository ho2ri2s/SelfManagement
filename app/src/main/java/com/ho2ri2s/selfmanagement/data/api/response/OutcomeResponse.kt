package com.ho2ri2s.selfmanagement.data.api.response

import com.squareup.moshi.Json

data class OutcomeResponse(
    @Json(name = "outcome_uuid") val id: String,
    val amount: Int,
    val title: String,
    val day: Int,
)
