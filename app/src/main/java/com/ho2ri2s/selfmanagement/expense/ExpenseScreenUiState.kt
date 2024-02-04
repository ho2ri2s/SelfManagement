package com.ho2ri2s.selfmanagement.expense

import com.ho2ri2s.selfmanagement.model.Expense
import java.time.LocalDateTime

data class ExpenseScreenUiState(
    val expense: Expense?,
    val currentDate: LocalDateTime,
) {

    fun getYearMonth(): String {
        return "${currentDate.year}年${currentDate.monthValue}月"
    }
}
