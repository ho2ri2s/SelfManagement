package com.ho2ri2s.selfmanagement.signup

import com.ho2ri2s.selfmanagement.signup.EmailState
import com.ho2ri2s.selfmanagement.signup.PasswordState

data class SignupScreenUiState(
  val emailState: EmailState,
  val passwordState: PasswordState,
  val isEnabledSignup: Boolean,
)
