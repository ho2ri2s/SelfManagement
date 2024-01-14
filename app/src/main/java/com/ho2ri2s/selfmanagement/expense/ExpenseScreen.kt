package com.ho2ri2s.selfmanagement.expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.google.common.collect.ImmutableList
import com.ho2ri2s.selfmanagement.R
import com.ho2ri2s.selfmanagement.model.Income
import com.ho2ri2s.selfmanagement.model.Outcome
import kotlinx.coroutines.launch

@Composable
fun ExpenseScreen(
    viewModel: ExpenseViewModel = hiltViewModel(),
) {
    val uiState by viewModel.expenseUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.onShowScreen()
    }
    ExpenseScreen(uiState)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpenseScreen(
    uiState: ExpenseScreenUiState,
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetContent = {
            InputIncomeScreen()
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(16.dp),
    ) {
        Scaffold(
            topBar = { ExpenseAppBar(onClickCreateButton = { sheetState.show() }) },
            backgroundColor = MaterialTheme.colors.background,
        ) { innerPadding ->
            Column(
                modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(24.dp),
            ) {
                ExpensePieChart()
                Spacer(modifier = Modifier.height(16.dp))
                IncomeComponent(uiState.expense?.income)
                Spacer(modifier = Modifier.height(16.dp))
                OutcomeList(uiState.expense?.outcomes ?: ImmutableList.of())
            }
        }
    }
}

@Composable
private fun ExpenseAppBar(
    onClickCreateButton: suspend () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text(
                text = "2024年1月",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        },
        actions = {
            Box(
                modifier =
                Modifier
                    .size(60.dp)
                    .clickable { scope.launch { onClickCreateButton() } },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        },
        modifier = modifier.height(60.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.onPrimary,
    )
}

@Composable
private fun ExpensePieChart(modifier: Modifier = Modifier) {
    Surface(
        modifier =
        modifier
            .fillMaxWidth()
            .height(300.dp),
        color = MaterialTheme.colors.surface,
    ) {
        val slice =
            PieChartData.Slice(
                value = 0.3f,
                color = MaterialTheme.colors.primary,
            )
        val slice2 =
            PieChartData.Slice(
                value = 0.7f,
                color = MaterialTheme.colors.secondary,
            )
        PieChart(
            pieChartData = PieChartData(listOf(slice, slice2)),
            modifier = Modifier.padding(32.dp),
        )
    }
}

@Composable
private fun IncomeComponent(
    income: Income?,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.income_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "2024年1月", fontSize = 16.sp)
                Spacer(modifier = Modifier.weight(1f))
                val amount = income?.separatedAmount() ?: "0"
                Text(text = "¥$amount", fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun OutcomeList(
    outcomes: ImmutableList<Outcome>,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Text(
                    text = stringResource(id = R.string.outcome_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            itemsIndexed(outcomes) { index, outcome ->
                OutcomeItem(outcome)
                if (index != outcomes.size - 1) {
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun OutcomeItem(outcome: Outcome) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(vertical = 8.dp),
    ) {
        Text(text = outcome.title, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "¥${outcome.separatedAmount()}", fontSize = 16.sp)
    }
}
