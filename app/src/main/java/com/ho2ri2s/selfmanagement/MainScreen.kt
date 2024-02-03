package com.ho2ri2s.selfmanagement

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ho2ri2s.selfmanagement.expense.ExpenseScreen
import com.ho2ri2s.selfmanagement.signup.SignupScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "signup") {
        composable("expense") {
            ExpenseScreen(
                viewModel = hiltViewModel(),
            )
        }
        composable("signup") {
            SignupScreen(
                viewModel = hiltViewModel(),
                onClickSignup = {
                    navController.navigate("expense")
                },
            )
        }
    }
}
