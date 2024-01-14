package com.ho2ri2s.selfmanagement.data.api

import com.squareup.moshi.Json
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

class ExpenseApiClient
    @Inject
    constructor(
        private val service: Service,
    ) {
        constructor(retrofit: Retrofit) : this(
            service = retrofit.create<Service>(),
        )

        interface Service {
            @POST("/income/create")
            suspend fun createIncome(
                @Body createIncomeRequest: CreateIncomeRequest,
            )
        }

        suspend fun createIncome(
            year: Int,
            month: Int,
            amount: Int,
        ) {
            val request =
                CreateIncomeRequest(
                    year = year,
                    month = month,
                    amount = amount,
                )
            service.createIncome(request)
        }

        data class CreateIncomeRequest(
            @Json(name = "user_uuid")
            val year: Int,
            val month: Int,
            val amount: Int,
        )
    }
