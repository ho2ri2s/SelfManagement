package com.ho2ri2s.selfmanagement.signup

data class SignupScreenUiState(
    val emailState: EmailState,
    val passwordState: PasswordState,
    val isEnabledSignup: Boolean,
)
