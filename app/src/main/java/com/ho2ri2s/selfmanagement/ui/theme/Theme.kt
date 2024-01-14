package com.ho2ri2s.selfmanagement.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// TODO: tonal color palette 決める
private val SelfManagementColors =
    lightColors(
        primary = DarkBlue,
        primaryVariant = DarkBlue,
        onPrimary = Color.White,
        secondary = MediumEmphasis,
        secondaryVariant = MediumEmphasisDark,
        onSecondary = Color.White,
        error = Red800,
        background = WeakWhite,
        surface = Color.White,
        onSurface = Color.Black,
    )

@Composable
fun SelfManagementTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = SelfManagementColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colors = colorScheme,
        content = content,
    )
}
