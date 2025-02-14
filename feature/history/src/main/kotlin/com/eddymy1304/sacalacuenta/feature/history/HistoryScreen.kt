package com.eddymy1304.sacalacuenta.feature.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.eddymy1304.sacalacuenta.core.designsystem.component.DatePickerDialog
import com.eddymy1304.sacalacuenta.core.designsystem.icon.AppIcons
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme
import com.eddymy1304.sacalacuenta.core.model.ReceiptWithDetail
import com.eddymy1304.sacalacuenta.core.ui.ItemHistory
import com.eddymy1304.sacalacuenta.feature.history.utils.Utils

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel(),
    configScreen: () -> Unit,
    navToTicketScreen: (id: Int) -> Unit
) {

    LaunchedEffect(Unit) { configScreen() }

    val listReceipts by viewModel.listReceiptWithDet.collectAsState()
    val showDatePicker by viewModel.showDatePicker.collectAsState()
    val date by viewModel.date.collectAsState()

    val showAlertDialog by viewModel.showAlertDialog.collectAsState()

    val context = LocalContext.current.applicationContext

    HistoryScreen(
        date = date,
        listReceipts = listReceipts,
        onClickIconExport = { viewModel.setShowAlertDialog(true) },
        showAlertDialog = showAlertDialog,
        onDismissAlertDialog = { viewModel.setShowAlertDialog(false) },
        onConfirmAlertDialog = {
            Utils.exportToCsv(context, listReceipts)
            viewModel.setShowAlertDialog(false)
        },
        showDatePicker = showDatePicker,
        modifier = modifier,
        onDismissDatePicker = { viewModel.setShowDatePicker(false) },
        onDateSelectedDatePicker = { d ->
            if (d.isNotBlank()) viewModel.getTicketsByDate(d)
        },
        onClickIconDate = { viewModel.setShowDatePicker(true) }
    ) { receiptWithDetail ->
        navToTicketScreen(receiptWithDetail.receipt.id)
    }
}

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    date: String = "",
    showAlertDialog: Boolean = false,
    onDismissAlertDialog: () -> Unit = {},
    onConfirmAlertDialog: () -> Unit = {},
    showDatePicker: Boolean = false,
    onClickIconDate: () -> Unit = {},
    onClickIconExport: () -> Unit = {},
    onDismissDatePicker: () -> Unit = {},
    onDateSelectedDatePicker: (String) -> Unit = {},
    listReceipts: List<ReceiptWithDetail> = listOf(),
    onClickItem: (ReceiptWithDetail) -> Unit
) {

    ConstraintLayout(modifier.fillMaxSize()) {
        val (dt, list, total) = createRefs()

        OutlinedTextField(
            singleLine = true,
            readOnly = true,
            modifier = Modifier.constrainAs(dt) {
                top.linkTo(parent.top, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            },
            value = Utils.formatDate(date),
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.hint_date)) },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable { onClickIconDate() },
                    imageVector = AppIcons.History,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )

        LazyColumn(
            modifier = Modifier.constrainAs(list) {
                top.linkTo(dt.bottom)
                bottom.linkTo(total.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listReceipts) {
                ItemHistory(receipt = it.receipt) {
                    onClickItem(it)
                }
            }
        }

        val totalByDay = listReceipts.sumOf { it.receipt.total }

        Text(
            modifier = Modifier
                .constrainAs(total) {
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .clickable { onClickIconExport() },
            text = stringResource(id = R.string.total_by_day, totalByDay),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )
    }

    AnimatedVisibility(visible = showDatePicker) {
        DatePickerDialog(
            onDismiss = onDismissDatePicker,
            onDateSelected = onDateSelectedDatePicker
        )
    }

    AnimatedVisibility(visible = showAlertDialog) {
        AlertDialog(
            onDismissRequest = onDismissAlertDialog,
            title = { Text(text = stringResource(id = R.string.title_export_to_csv)) },
            text = { Text(text = stringResource(id = R.string.message_export_to_csv)) },
            confirmButton = {
                TextButton(onClick = onConfirmAlertDialog) {
                    Text(text = stringResource(id = com.eddymy1304.sacalacuenta.core.designsystem.R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissAlertDialog) {
                    Text(text = stringResource(id = com.eddymy1304.sacalacuenta.core.designsystem.R.string.cancel))
                }
            })
    }
}

@Preview(showBackground = true, locale = "es")
@Composable
fun PreviewHistoryScreen() {
    SacaLaCuentaTheme {
        HistoryScreen(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}