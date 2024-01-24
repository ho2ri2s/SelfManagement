package com.ho2ri2s.selfmanagement.data.api

import com.google.common.collect.ImmutableList
import com.ho2ri2s.selfmanagement.data.api.response.ExpenseResponse
import com.ho2ri2s.selfmanagement.data.api.response.IncomeResponse
import com.ho2ri2s.selfmanagement.data.api.response.OutcomeResponse
import com.ho2ri2s.selfmanagement.model.Expense
import com.ho2ri2s.selfmanagement.model.Income
import com.ho2ri2s.selfmanagement.model.IncomeId
import com.ho2ri2s.selfmanagement.model.Outcome
import com.ho2ri2s.selfmanagement.model.OutcomeId
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject

/**
 * Errorハンドリング
 */
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

        @GET("/expense/get")
        suspend fun getExpense(
            @Query("year") year: Int,
            @Query("month") month: Int,
        ): ExpenseResponse
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

    suspend fun getExpense(
        year: Int,
        month: Int,
    ): Expense {
        val response = service.getExpense(year, month)
        return response.toModel()
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

    private fun IncomeResponse.toModel(): Income {
        return Income(
            id = IncomeId(id),
            amount = amount,
        )
    }

    private fun OutcomeResponse.toModel(): Outcome {
        return Outcome(
            id = OutcomeId(id),
            amount = amount,
            title = title,
            day = day,
        )
    }

    private fun ExpenseResponse.toModel(): Expense {
        return Expense(
            year = year,
            month = month,
            income = income.toModel(),
            outcomes = ImmutableList.copyOf(outcomes.map { it.toModel() }),
        )
    }
}
