package com.ho2ri2s.selfmanagement.data.repository

import com.ho2ri2s.selfmanagement.model.User

interface AuthRepository {
  suspend fun signupWithEmailPassword(
    email: String,
    password: String,
  )

  suspend fun register(
    email: String,
    name: String,
  ): User
}