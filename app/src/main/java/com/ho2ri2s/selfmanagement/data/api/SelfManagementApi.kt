package com.ho2ri2s.selfmanagement.data.api

import com.ho2ri2s.selfmanagement.data.api.response.UserResponse
import com.ho2ri2s.selfmanagement.model.User
import com.ho2ri2s.selfmanagement.model.UserId
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

class SelfManagementApi @Inject constructor(
  private val service: Service
) {
  constructor(retrofit: Retrofit) : this(retrofit.create<Service>())

  interface Service {
    @GET("/user/{userId}")
    suspend fun getUser(
      @Path("userId") userId: String,
    ): UserResponse

    @POST("/user")
    suspend fun register(
      @Body registerRequest: RegisterRequest,
    ): UserResponse
  }

  suspend fun getUser(
    userId: String,
  ): User {
    return service.getUser(userId).toModel()
  }

  suspend fun register(
    registerRequest: RegisterRequest,
  ): User {
    return service.register(registerRequest).toModel()
  }

  data class RegisterRequest(
    val email: String,
    val name: String,
  )

  private fun UserResponse.toModel(): User {
    return User(
      id = UserId(id),
      name = name,
    )
  }
}