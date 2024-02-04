package com.ho2ri2s.selfmanagement.expense

import java.time.LocalDateTime
import java.time.YearMonth

data class InputOutcomeScreenUiState(
    val title: String,
    val amount: String,
    val currentDate: LocalDateTime,
) {

    fun getTargetDate(): String {
        return "${currentDate.year}年${currentDate.monthValue}月${currentDate.dayOfMonth}日"
    }

    fun targetDayOfMonth(): Int {
        val yearMonth = YearMonth.from(currentDate)
        return yearMonth.lengthOfMonth()
    }
}
