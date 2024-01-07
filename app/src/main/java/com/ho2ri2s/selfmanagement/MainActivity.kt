package com.ho2ri2s.selfmanagement

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.ho2ri2s.selfmanagement.ui.theme.SelfManagementTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @RequiresApi(Build.VERSION_CODES.R)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window.decorView.windowInsetsController?.run {
      hide(android.view.WindowInsets.Type.statusBars())
    }
    setContent {
      SelfManagementTheme {
        MainScreen()
      }
    }
  }
}