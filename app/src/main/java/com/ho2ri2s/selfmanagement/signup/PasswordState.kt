package com.ho2ri2s.selfmanagement.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PasswordState private constructor() : ValidationState {
    var value: String by mutableStateOf("")
        private set

    private var mutableType: ValidationType by mutableStateOf(ValidationType.Initial)
    override val type: ValidationType
        get() = mutableType

    override fun isValid(value: String): Boolean {
        if (value.length < 6) return false
        val passwordPattern = "[A-Za-z0-9-_]+".toRegex()
        return passwordPattern.matches(value)
    }

    override fun onChangedValue(value: String): PasswordState {
        val type =
            if (isValid(value)) {
                ValidationType.Valid
            } else {
                ValidationType.Error
            }
        return PasswordState().apply {
            this.value = value
            this.mutableType = type
        }
    }

    companion object {
        val Initial = PasswordState()
    }
}
