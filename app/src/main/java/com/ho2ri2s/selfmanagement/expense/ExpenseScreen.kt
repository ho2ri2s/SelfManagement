package com.ho2ri2s.selfmanagement.expense

import androidx.compose.foundation.background
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
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.google.common.collect.ImmutableList
import com.ho2ri2s.selfmanagement.R
import com.ho2ri2s.selfmanagement.model.Income
import com.ho2ri2s.selfmanagement.model.Outcome
import com.ho2ri2s.selfmanagement.ui.theme.DarkBlue
import kotlinx.coroutines.launch

@Composable
fun ExpenseScreen(
    viewModel: ExpenseViewModel = hiltViewModel(),
) {
    val uiState by viewModel.expenseUiState.collectAsStateWithLifecycle()
    val needReload by viewModel.needReloadStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.onShowScreen()
    }
    LaunchedEffect(needReload) {
        if (needReload) {
            viewModel.onReload()
        }
    }
    ExpenseScreen(
        uiState = uiState,
        onClickCalendarLeft = viewModel::onClickCalendarLeft,
        onClickCalendarRight = viewModel::onClickCalendarRight,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpenseScreen(
    uiState: ExpenseScreenUiState,
    onClickCalendarLeft: () -> Unit = {},
    onClickCalendarRight: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var isIncomeInputMode by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    val onClickSave: () -> Unit = {
        coroutineScope.launch {
            sheetState.hide()
        }
    }
    ModalBottomSheetLayout(
        sheetContent = {
            if (isIncomeInputMode) {
                InputIncomeBottomSheet(
                    currentDate = uiState.currentDate,
                    onClickSave = onClickSave
                )
            } else {
                InputOutcomeBottomSheet(
                    currentDate = uiState.currentDate,
                    onClickSave = onClickSave,
                )
            }
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
    ) {
        Scaffold(
            topBar = {
                ExpenseAppBar(
                    yearMonth = uiState.getYearMonth(),
                    onClickCalendarLeft = onClickCalendarLeft,
                    onClickCalendarRight = onClickCalendarRight,
                )
            },
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
                IncomeComponent(
                    income = uiState.expense?.income,
                    onCreateIncomeClicked = {
                        isIncomeInputMode = true
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutcomeList(
                    outcomes = uiState.expense?.outcomes ?: ImmutableList.of(),
                    onCreateOutcomeClicked = {
                        isIncomeInputMode = false
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun ExpenseAppBar(
    yearMonth: String,
    modifier: Modifier = Modifier,
    onClickCalendarLeft: () -> Unit = {},
    onClickCalendarRight: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    IconButton(onClick = onClickCalendarLeft) {
                        Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = null)
                    }
                    Text(
                        text = yearMonth,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                    IconButton(onClick = onClickCalendarRight) {
                        Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null)
                    }
                }
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
                label = "食費",
            )
        val slice2 =
            PieChartData.Slice(
                value = 0.7f,
                color = MaterialTheme.colors.secondary,
                label = "その他",
            )
        val data = PieChartData(
            slices = listOf(slice, slice2),
            plotType = PlotType.Pie,
        )
        val config = PieChartConfig(
            isAnimationEnable = true,
            showSliceLabels = true,
            animationDuration = 500
        )
        PieChart(
            pieChartData = data,
            pieChartConfig = config,
            modifier = Modifier.padding(32.dp),
        )
    }
}

@Composable
private fun IncomeComponent(
    income: Income?,
    modifier: Modifier = Modifier,
    onCreateIncomeClicked: () -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.height(48.dp)) {
                Text(
                    text = stringResource(id = R.string.income),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
                if (income == null) {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { onCreateIncomeClicked() },
                        modifier = Modifier.background(DarkBlue, RoundedCornerShape(4.dp)),
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.income_create),
                            tint = Color.White,
                        )
                    }
                }
            }
            if (income != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "2024年1月", fontSize = 16.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    val amount = income.separatedAmount()
                    Text(text = "¥$amount", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
private fun OutcomeList(
    outcomes: ImmutableList<Outcome>,
    modifier: Modifier = Modifier,
    onCreateOutcomeClicked: () -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Row(modifier = Modifier.height(48.dp)) {
                    Text(
                        text = stringResource(id = R.string.outcome),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { onCreateOutcomeClicked() },
                        modifier = Modifier
                            .background(DarkBlue, RoundedCornerShape(4.dp)),
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.outcome_create),
                            tint = Color.White,
                        )
                    }
                }
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
