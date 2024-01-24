package com.ho2ri2s.selfmanagement.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ho2ri2s.selfmanagement.data.repository.ExpenseRepository
import com.ho2ri2s.selfmanagement.ext.buildUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputOutcomeViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {

    private val mutableTitleStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val mutableAmountStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    val uiState = buildUiState(
        mutableTitleStateFlow,
        mutableAmountStateFlow,
    ) { title, amount ->
        InputOutcomeScreenUiState(
            title = title,
            amount = amount,
        )
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
                expenseRepository.createOutcome(
                    year = 2024,
                    month = 1,
                    day = 1,
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
}