package com.ho2ri2s.selfmanagement.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ho2ri2s.selfmanagement.data.repository.AuthRepository
import com.ho2ri2s.selfmanagement.ext.buildUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
  private val authRepository: AuthRepository,
) : ViewModel() {
  private val mutableEmailStateFlow: MutableStateFlow<EmailState> =
    MutableStateFlow(EmailState.Initial)
  private val mutablePasswordStateFlow: MutableStateFlow<PasswordState> =
    MutableStateFlow(PasswordState.Initial)

  private val mutableSnackBarState: MutableStateFlow<SignupSnackBarType?> = MutableStateFlow(null)
  val snackBarState: StateFlow<SignupSnackBarType?> = mutableSnackBarState.asStateFlow()

  val uiState = buildUiState(
    mutableEmailStateFlow,
    mutablePasswordStateFlow,
  ) { email, password ->
    val isEnabled = email.type.isValid && password.type.isValid
    SignupScreenUiState(
      emailState = email,
      passwordState = password,
      isEnabledSignup = isEnabled,
    )
  }

  fun onChangeEmail(value: String) {
    mutableEmailStateFlow.update { it.onChangedValue(value) }
  }

  fun onChangePassword(value: String) {
    mutablePasswordStateFlow.update { it.onChangedValue(value) }
  }

  fun onClickSignup() {
    if (!uiState.value.emailState.type.isValid) return
    if (!uiState.value.passwordState.type.isValid) return
    viewModelScope.launch {
      runCatching {
        authRepository.signupWithEmailPassword(
          uiState.value.emailState.value,
          uiState.value.passwordState.value
        )
      }
        .onSuccess {
          registerUser()
        }
        .onFailure {
          Timber.e(it, it.message)
          mutableSnackBarState.value = SignupSnackBarType.FailedAuth
        }
    }
  }

  fun onShownSnackBar() {
    mutableSnackBarState.value = null
  }

  private suspend fun registerUser() {
    runCatching {
      authRepository.register(uiState.value.emailState.value, "testhoris")
    }
      .onSuccess {
        mutableSnackBarState.value = SignupSnackBarType.SuccessAuth
      }
      .onFailure {
        Timber.e(it, it.message)
        mutableSnackBarState.value = SignupSnackBarType.FailedAuth
      }
  }

}

enum class SignupSnackBarType {
  FailedAuth,
  SuccessAuth, // TODO: いらない
}