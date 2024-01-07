package com.ho2ri2s.selfmanagement.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.ho2ri2s.selfmanagement.data.api.SelfManagementApi
import com.ho2ri2s.selfmanagement.model.User
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val auth: FirebaseAuth,
        private val selfManagementApi: SelfManagementApi,
    ) : AuthRepository {
        override suspend fun signupWithEmailPassword(
            email: String,
            password: String,
        ) {
            if (auth.currentUser != null) {
                // TODO: すでにログイン済みなので、ログイン画面に飛ばす
                throw IllegalStateException("Already SignIn")
            }
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Timber.d("mytag user = ${result.user?.email}, ${result.user?.uid}")
        }

        override suspend fun register(
            email: String,
            name: String,
        ): User {
            // TODO: name入力
            val name = "testhoris"
            return selfManagementApi.register(SelfManagementApi.RegisterRequest(email, name))
        }
    }
