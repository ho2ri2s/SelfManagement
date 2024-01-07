package com.ho2ri2s.selfmanagement.signup

interface ValidationState {
    val type: ValidationType

    fun isValid(value: String): Boolean

    fun onChangedValue(value: String): ValidationState
}

enum class ValidationType {
    Initial,
    Error,
    Valid,
    ;

    val isInitial
        get() = this == Initial
    val isError
        get() = this == Error
    val isValid
        get() = this == Valid
}
