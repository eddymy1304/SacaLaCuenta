package com.example.sacalacuenta.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sacalacuenta.R
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme
import com.example.sacalacuenta.utils.Utils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    minDate: String? = null,
    maxDate: String? = null,
    onDismiss: () -> Unit = {},
    onDateSelected: (String) -> Unit = {}
) {

    val minDateMinusDay = Utils.chanceDayFromDate(minDate.orEmpty(), -1)
    val maxDatePlusDay = Utils.chanceDayFromDate(maxDate.orEmpty(), 1)

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = if (minDate != null) Utils.calendarDateToLong(minDate) else null,
        selectableDates =
        object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return when {
                    minDate != null && maxDate != null ->
                        utcTimeMillis >= Utils.calendarDateToLong(minDateMinusDay) &&
                                utcTimeMillis <= Utils.calendarDateToLong(maxDate)

                    minDate == null && maxDate != null ->
                        utcTimeMillis <= Utils.calendarDateToLong(maxDate)

                    minDate != null && maxDate == null ->
                        utcTimeMillis >= Utils.calendarDateToLong(minDateMinusDay)

                    else -> utcTimeMillis <= System.currentTimeMillis()
                }
            }
        }

    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = { onDateSelected(selectedDate) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )

            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        }, dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Color.White
        )
    ) {
        DatePicker(
            /*            colors = DatePickerDefaults.colors(
                            containerColor = Color.White,
                            currentYearContentColor = MaterialTheme.colorScheme.primary,
                            selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                            yearContentColor = MaterialTheme.colorScheme.secondary,

                            selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                            todayContentColor = MaterialTheme.colorScheme.primary,
                            todayDateBorderColor = MaterialTheme.colorScheme.primary,

                            dateTextFieldColors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary
                            )
                        ),*/
            state = datePickerState
        )
    }

}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(Utils.convertUtcToLocal(millis)))
}

@Preview(showBackground = true)
@Composable
fun MyDatePickerDialogPreview() {
    SacaLaCuentaTheme {
        DatePickerDialog()
    }
}