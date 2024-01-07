package com.ho2ri2s.selfmanagement.data.api

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor
    @Inject
    constructor(
        private val auth: FirebaseAuth,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val token =
                runBlocking {
                    auth.currentUser?.getIdToken(true)?.await()?.token
                }
            Timber.d("mytag token = $token")
            if (token == null) {
                Timber.e("token is null")
                return chain.proceed(chain.request())
            }
            val newRequest =
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()

            return chain.proceed(newRequest)
        }
    }
