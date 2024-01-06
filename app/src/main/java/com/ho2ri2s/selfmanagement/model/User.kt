package com.ho2ri2s.selfmanagement.model

data class User(
  val id: UserId,
  val name: String,
)


data class UserId(
  val value: String
)