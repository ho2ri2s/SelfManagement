package com.ho2ri2s.selfmanagement.data.api.response

import com.squareup.moshi.Json

data class UserResponse(
  @Json(name = "user_uuid")
  val id: String,
  @Json(name = "name")
  val name: String,
  @Json(name = "email")
  val email: String,
)
