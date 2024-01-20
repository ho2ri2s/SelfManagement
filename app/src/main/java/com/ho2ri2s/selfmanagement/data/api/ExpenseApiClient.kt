package com.ho2ri2s.selfmanagement.data.api

import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject

class ExpenseApiClient @Inject constructor(
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

        @POST("/outcome/create")
        suspend fun createOutcome(
            @Body createOutcomeRequest: CreateOutcomeRequest,
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

    suspend fun createOutcome(
        year: Int,
        month: Int,
        day: Int,
        title: String,
        amount: Int,
    ) {
        val request =
            CreateOutcomeRequest(
                year = year,
                month = month,
                day = day,
                title = title,
                amount = amount,
            )
        service.createOutcome(request)
    }

    data class CreateIncomeRequest(
        val year: Int,
        val month: Int,
        val amount: Int,
    )

    data class CreateOutcomeRequest(
        val year: Int,
        val month: Int,
        val day: Int,
        val title: String,
        val amount: Int,
    )
}
