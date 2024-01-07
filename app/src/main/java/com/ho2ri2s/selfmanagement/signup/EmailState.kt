package com.ho2ri2s.selfmanagement.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class EmailState private constructor() : ValidationState {
    var value: String by mutableStateOf("")
        private set

    private var mutableType: ValidationType by mutableStateOf(ValidationType.Initial)
    override val type: ValidationType
        get() = mutableType

    override fun isValid(value: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return emailRegex.matches(value)
    }

    override fun onChangedValue(value: String): EmailState {
        val type =
            if (isValid(value)) {
                ValidationType.Valid
            } else {
                ValidationType.Error
            }
        return EmailState().apply {
            this.value = value
            this.mutableType = type
        }
    }

    companion object {
        val Initial = EmailState()
    }
}
