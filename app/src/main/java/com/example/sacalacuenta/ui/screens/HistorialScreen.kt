package com.example.sacalacuenta.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.example.sacalacuenta.MainViewModel
import com.example.sacalacuenta.R
import com.example.sacalacuenta.data.models.CuentaWithDetalleView
import com.example.sacalacuenta.data.models.ScreenTicket
import com.example.sacalacuenta.ui.components.DatePickerDialog
import com.example.sacalacuenta.ui.components.ItemHistorial
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme
import com.example.sacalacuenta.utils.Utils

@Composable
fun HistorialScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    LaunchedEffect(Unit) { viewModel.getTicketsByDate() }

    val listCuentas by viewModel.listCuentaWithDetalle.collectAsState()
    val showDatePicker by viewModel.showDatePicker.collectAsState()
    val fecha by viewModel.fecha.collectAsState()

    var showAlertDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current.applicationContext
    HistorialScreen(
        fecha = fecha,
        listCuentas = listCuentas,
        onClickIconExport = {
            showAlertDialog = true
        },
        showAlertDialog = showAlertDialog,
        onDismissAlertDialog = { showAlertDialog = false },
        onConfirmAlertDialog = {
            Utils.exportToCsv(context, listCuentas)
            showAlertDialog = false
        },
        showDatePicker = showDatePicker,
        modifier = modifier,
        onDismissDatePicker = { viewModel.updateShowDatePicker(false) },
        onDateSelectedDatePicker = {
            if (it.isNotBlank()) {
                viewModel.updateFecha(it)
                viewModel.updateShowDatePicker(false)
                viewModel.getTicketsByDate()
            }
        },
        onClickIconDate = { viewModel.updateShowDatePicker(true) }
    ) {
        viewModel.updateCuentaWithDetalle(it)
        navController.navigate(ScreenTicket)
    }
}

@Composable
fun HistorialScreen(
    modifier: Modifier = Modifier,
    fecha: String = "",
    showAlertDialog: Boolean = false,
    onDismissAlertDialog: () -> Unit = {},
    onConfirmAlertDialog: () -> Unit = {},
    showDatePicker: Boolean = false,
    onClickIconDate: () -> Unit = {},
    onClickIconExport: () -> Unit = {},
    onDismissDatePicker: () -> Unit = {},
    onDateSelectedDatePicker: (String) -> Unit = {},
    listCuentas: List<CuentaWithDetalleView> = listOf(),
    onClickItem: (CuentaWithDetalleView) -> Unit
) {

    ConstraintLayout(modifier) {
        val (date, list, total) = createRefs()

        OutlinedTextField(
            singleLine = true,
            readOnly = true,
            modifier = Modifier.constrainAs(date) {
                top.linkTo(parent.top, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            },
            value = Utils.formatDate(fecha),
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.hint_fecha)) },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable { onClickIconDate() },
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )

        LazyColumn(
            modifier = Modifier.constrainAs(list) {
                top.linkTo(date.bottom)
                bottom.linkTo(total.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listCuentas) {
                ItemHistorial(cuenta = it.cuenta) {
                    onClickItem(it)
                }
            }
        }

        val totalByDay = listCuentas.sumOf { it.cuenta.total.value ?: 0.0 }

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
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissAlertDialog) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            })
    }
}

@Preview(showBackground = true, showSystemUi = true, locale = "es")
@Composable
fun PreviewHistorialScreen() {
    SacaLaCuentaTheme {
        HistorialScreen(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}