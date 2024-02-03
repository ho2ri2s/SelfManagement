package com.ho2ri2s.selfmanagement.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ho2ri2s.selfmanagement.data.repository.ExpenseRepository
import com.ho2ri2s.selfmanagement.ext.buildUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class InputOutcomeViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {

    private val mutableTitleStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val mutableAmountStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val mutableCurrentDateStateFlow = MutableStateFlow(LocalDateTime.now())


    val uiState = buildUiState(
        mutableTitleStateFlow,
        mutableAmountStateFlow,
        mutableCurrentDateStateFlow
    ) { title, amount, currentDate ->
        InputOutcomeScreenUiState(
            title = title,
            amount = amount,
            currentDate = currentDate,
        )
    }

    fun setup(currentDate: LocalDateTime) {
        mutableCurrentDateStateFlow.value = currentDate
    }

    fun onChangeOutcomeTitle(title: String) {
        mutableTitleStateFlow.value = title
    }

    fun onChangeOutcomeAmount(amount: String) {
        mutableAmountStateFlow.value = amount
    }

    fun onClickSave() {
        viewModelScope.launch {
            if (isInputDataValid()) {
                val currentDate = mutableCurrentDateStateFlow.value
                expenseRepository.createOutcome(
                    year = currentDate.year,
                    month = currentDate.monthValue,
                    day = currentDate.dayOfMonth,
                    title = mutableTitleStateFlow.value,
                    amount = mutableAmountStateFlow.value.toInt(),
                )
            }
        }
    }

    private fun isInputDataValid(): Boolean {
        val isTitleValid = mutableTitleStateFlow.value.isNotBlank()
        val isAmountValid = mutableAmountStateFlow.value.toIntOrNull() != null
        return isTitleValid && isAmountValid
    }

    fun onInputDaySelected(newValue: Int) {
        val currentDate = mutableCurrentDateStateFlow.value
        mutableCurrentDateStateFlow.value = currentDate.withDayOfMonth(newValue)
    }
}