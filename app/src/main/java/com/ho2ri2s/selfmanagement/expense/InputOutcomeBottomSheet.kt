package com.ho2ri2s.selfmanagement.expense

import android.widget.NumberPicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ho2ri2s.selfmanagement.R
import com.ho2ri2s.selfmanagement.ui.theme.MediumEmphasis
import java.time.LocalDateTime

@Composable
fun InputOutcomeBottomSheet(
    currentDate: LocalDateTime,
    onClickSave: () -> Unit,
    viewModel: InputOutcomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = currentDate, block = {
        viewModel.setup(currentDate)
    })
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    InputOutcomeBottomSheet(
        uiState = uiState,
        onChangeOutcomeTitle = viewModel::onChangeOutcomeTitle,
        onChangeOutcomeAmount = viewModel::onChangeOutcomeAmount,
        onInputDayChanged = viewModel::onInputDaySelected,
        onClickSave = {
            onClickSave()
            viewModel.onClickSave()
        },
    )
}

@Composable
fun InputOutcomeBottomSheet(
    uiState: InputOutcomeScreenUiState,
    onChangeOutcomeTitle: (String) -> Unit = {},
    onChangeOutcomeAmount: (String) -> Unit = {},
    onInputDayChanged: (Int) -> Unit = {},
    onClickSave: () -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(36.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var inputDayDialogState by remember { mutableStateOf(false) }
        Text(
            text = stringResource(id = R.string.outcome),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        // TODO: IMEの表示でレイアウトの位置を変える
        Box(
            modifier = Modifier
                .height(56.dp)
                .border(1.dp, MediumEmphasis, RoundedCornerShape(4.dp))
                .clickable { inputDayDialogState = true },
            contentAlignment = Alignment.CenterStart
        )
        {
            Text(
                text = uiState.getTargetDate(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }
        if (inputDayDialogState) {
            InputDayDialog(
                currentDay = uiState.currentDate.dayOfMonth,
                targetDayOfMonth = uiState.targetDayOfMonth(),
                onInputDayChanged = onInputDayChanged,
                onDismissRequest = { inputDayDialogState = false }
            )
        }
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
            maxLines = 1,
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
            maxLines = 1,
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

@Composable
private fun InputDayDialog(
    currentDay: Int,
    targetDayOfMonth: Int,
    onInputDayChanged: (Int) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    var selectedDay by remember { mutableIntStateOf(1) }
    Dialog(
        onDismissRequest = {
            onInputDayChanged(selectedDay)
            onDismissRequest()
        }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
        ) {
            AndroidView(
                factory = { context ->
                    NumberPicker(context).apply {
                        minValue = 1
                        maxValue = targetDayOfMonth
                        setOnValueChangedListener { _, _, newVal ->
                            selectedDay = newVal
                        }
                        value = currentDay
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }
    }
}