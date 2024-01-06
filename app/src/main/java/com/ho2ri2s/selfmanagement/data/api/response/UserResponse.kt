package com.ho2ri2s.selfmanagement.data.api.response

import com.squareup.moshi.Json

data class UserResponse(
  @Json(name = "UserUUID")
  val id: String,
  @Json(name = "Name")
  val name: String,
  @Json(name = "Email")
  val email: String,
)
