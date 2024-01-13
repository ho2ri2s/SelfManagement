package com.ho2ri2s.selfmanagement.expense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ho2ri2s.selfmanagement.R

@Composable
fun InputIncomeScreen(viewModel: InputIncomeViewModel = hiltViewModel()) {
    val uiState by viewModel.amountUiState.collectAsStateWithLifecycle()
    InputIncomeScreen(
        uiState = uiState,
        onChangeIncomeAmount = viewModel::onChangeIncomeAmount,
        onClickSave = viewModel::onClickSave,
    )
}

@Composable
fun InputIncomeScreen(
    uiState: InputIncomeScreenUiState,
    onChangeIncomeAmount: (String) -> Unit = {},
    onClickSave: () -> Unit = {},
) {
    Scaffold { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(36.dp),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value = uiState.amount.toString(),
                onValueChange = onChangeIncomeAmount,
                keyboardOptions =
                    KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickSave,
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF102C66),
                        contentColor = Color.White,
                    ),
                enabled = true, // TODO: validation
            ) {
                Text(
                    text = stringResource(id = R.string.income_save),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
