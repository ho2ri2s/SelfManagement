package com.ho2ri2s.selfmanagement.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ho2ri2s.selfmanagement.R

// TODO: Navigation, enterキーでのタブ移動, パスワードの非表示
@Composable
fun SignupScreen(viewModel: SignupViewModel) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val snackBarState by viewModel.snackBarState.collectAsStateWithLifecycle()
  SignupScreen(
    uiState = uiState,
    snackBarType = snackBarState,
    onChangeEmail = viewModel::onChangeEmail,
    onChangePassword = viewModel::onChangePassword,
    onClickButton = viewModel::onClickSignup,
    onShownSnackBar = viewModel::onShownSnackBar,
  )
}

@Composable
fun SignupScreen(
  uiState: SignupScreenUiState,
  snackBarType: SignupSnackBarType?,
  onChangeEmail: (String) -> Unit,
  onChangePassword: (String) -> Unit,
  onClickButton: () -> Unit,
  onShownSnackBar: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val snackBarHostState = remember { SnackbarHostState() }
  val context = LocalContext.current
  LaunchedEffect(snackBarType) {
    snackBarType ?: return@LaunchedEffect
    val message = when (snackBarType) {
      SignupSnackBarType.SuccessAuth -> context.resources.getString(R.string.signup_welcome)
      SignupSnackBarType.FailedAuth -> context.resources.getString(R.string.signup_error_auth)
    }
    snackBarHostState.showSnackbar(message)
    onShownSnackBar()
  }
  Scaffold(
    backgroundColor = Color(0xFF102C66),
    snackbarHost = { SnackbarHost(snackBarHostState) },
  ) { parentPadding ->
    Column(
      modifier
        .fillMaxSize()
        .padding(parentPadding)
        .padding(horizontal = 16.dp),
    ) {
      SignupHeader(
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .weight(1f)
      )
      SignupInputBody(
        emailState = uiState.emailState,
        passwordState = uiState.passwordState,
        isEnabledSignup = uiState.isEnabledSignup,
        onChangeEmail = onChangeEmail,
        onChangePassword = onChangePassword,
        onClickButton = onClickButton,
      )
      Spacer(modifier = Modifier.weight(1f))
    }
  }
}

@Composable
fun SignupHeader(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
  ) {
    Text(
      text = stringResource(id = R.string.signup_welcome),
      fontSize = 24.sp,
      color = Color.White,
      fontWeight = FontWeight.Bold,
    )
    Text(
      text = stringResource(id = R.string.signup_welcome_message),
      fontSize = 18.sp,
      color = Color.White,
    )
  }
}

@Composable
fun SignupInputBody(
  emailState: EmailState,
  passwordState: PasswordState,
  isEnabledSignup: Boolean,
  onChangeEmail: (String) -> Unit,
  onChangePassword: (String) -> Unit,
  onClickButton: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
    Column(
      modifier = Modifier.padding(horizontal = 48.dp, vertical = 48.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      val focusManager = LocalFocusManager.current
      OutlinedTextField(
        value = emailState.value,
        onValueChange = onChangeEmail,
        isError = emailState.type.isError,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {
          focusManager.moveFocus(FocusDirection.Down)
        }),
        label = {
          Text(text = stringResource(id = R.string.signup_email_label))
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(),
        modifier = Modifier.fillMaxWidth(),
      )
      if (emailState.type.isError) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = stringResource(id = R.string.signup_error_email),
          color = Color.Red,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.align(Alignment.Start)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))
      OutlinedTextField(
        value = passwordState.value,
        onValueChange = onChangePassword,
        isError = passwordState.type.isError,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {
          focusManager.moveFocus(FocusDirection.Down)
        }),
        label = {
          Text(text = stringResource(id = R.string.signup_password_label))
        },
        modifier = Modifier.fillMaxWidth(),
      )
      if (passwordState.type.isError) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = stringResource(id = R.string.signup_error_password),
          color = Color.Red,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.align(Alignment.Start)
        )
      }

      Spacer(modifier = Modifier.height(32.dp))
      Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClickButton,
        colors = ButtonDefaults.buttonColors(
          backgroundColor = Color(0xFF102C66),
          contentColor = Color.White,
        ),
        enabled = isEnabledSignup,
      ) {
        Text(
          text = stringResource(id = R.string.signup_button),
          fontSize = 20.sp,
          color = Color.White,
          fontWeight = FontWeight.Bold,
        )
      }
    }
  }
}

@Composable
@Preview(device = Devices.PIXEL_4)
fun SignupScreenPreview() {
  val uiState = SignupScreenUiState(
    emailState = EmailState.Initial.let { it.onChangedValue("test@test.com") },
    passwordState = PasswordState.Initial.let { it.onChangedValue("testtest") },
    isEnabledSignup = true,
  )
  SignupScreen(
    uiState = uiState,
    snackBarType = null,
    onChangeEmail = {},
    onChangePassword = {},
    onClickButton = {},
    onShownSnackBar = {},
  )
}
