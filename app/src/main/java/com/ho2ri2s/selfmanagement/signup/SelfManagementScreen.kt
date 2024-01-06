package com.ho2ri2s.selfmanagement.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SelfManagementScreen(modifier: Modifier = Modifier) {
  Scaffold(modifier = modifier) { parentPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(parentPadding),
      contentAlignment = Alignment.Center,
    ) {
      Text(text = "SelfManagementScreen", fontSize = 24.sp)
    }
  }
}