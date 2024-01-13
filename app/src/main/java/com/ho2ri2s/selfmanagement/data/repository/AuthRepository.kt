package com.ho2ri2s.selfmanagement.data.repository

import com.ho2ri2s.selfmanagement.model.User
import com.ho2ri2s.selfmanagement.model.UserId

interface AuthRepository {
    suspend fun signupWithEmailPassword(
        email: String,
        password: String,
    )

    suspend fun register(
        email: String,
        name: String,
    ): User

    suspend fun getUserId(): UserId
}
