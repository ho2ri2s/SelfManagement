package com.ho2ri2s.selfmanagement.expense

import androidx.lifecycle.ViewModel
import com.ho2ri2s.selfmanagement.ext.buildUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class InputOutcomeViewModel @Inject constructor(

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

    }
}