package com.ho2ri2s.selfmanagement.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Scaffold { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Home")
        }
    }
}
