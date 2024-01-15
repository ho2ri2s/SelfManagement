package com.ho2ri2s.selfmanagement.expense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import com.ho2ri2s.selfmanagement.ui.theme.MediumEmphasis

@Composable
fun InputOutcomeBottomSheet(
    viewModel: InputOutcomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InputOutcomeBottomSheet(
        uiState = uiState,
        onChangeOutcomeTitle = viewModel::onChangeOutcomeTitle,
        onChangeOutcomeAmount = viewModel::onChangeOutcomeAmount,
        onClickSave = viewModel::onClickSave,
    )
}

@Composable
fun InputOutcomeBottomSheet(
    uiState: InputOutcomeScreenUiState,
    onChangeOutcomeTitle: (String) -> Unit = {},
    onChangeOutcomeAmount: (String) -> Unit = {},
    onClickSave: () -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(36.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.outcome),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        // TODO: IMEの表示でレイアウトの位置を変える
        OutlinedTextField(
            value = uiState.title,
            onValueChange = onChangeOutcomeTitle,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                placeholderColor = MediumEmphasis,
            ),
            label = {
                Text(text = stringResource(id = R.string.outcome_title))
            },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = uiState.amount,
            onValueChange = onChangeOutcomeAmount,
            keyboardOptions =
            KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                placeholderColor = MediumEmphasis,
            ),
            label = {
                Text(stringResource(id = R.string.outcome_amount))
            },
            modifier = Modifier.fillMaxWidth(),
        )
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
                text = stringResource(id = R.string.outcome_save),
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}