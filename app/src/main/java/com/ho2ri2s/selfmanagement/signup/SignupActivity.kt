package com.ho2ri2s.selfmanagement.signup

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ho2ri2s.selfmanagement.ui.theme.SelfManagementTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
  private val viewModel: SignupViewModel by viewModels()

  @RequiresApi(Build.VERSION_CODES.R)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window.decorView.windowInsetsController?.run {
      hide(android.view.WindowInsets.Type.statusBars())
    }
    setContent {
      SelfManagementTheme {
        SignupScreen(viewModel)
      }
    }
  }

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, SignupActivity::class.java)
    }
  }
}