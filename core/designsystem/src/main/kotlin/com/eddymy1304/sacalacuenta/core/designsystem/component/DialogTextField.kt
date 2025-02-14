package com.eddymy1304.sacalacuenta.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme
import com.eddymy1304.sacalacuenta.core.designsystem.R

@Composable
fun DialogTextField(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    textCancel: String = stringResource(R.string.cancel),
    textAccept: String = stringResource(R.string.accept),
    onValueChange: (String) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onClickAccept: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = text,
                    onValueChange = onValueChange
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = textCancel)
                    }
                    TextButton(onClick = onClickAccept) {
                        Text(text = textAccept)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogTextFieldPreview() {
    SacaLaCuentaTheme {
        DialogTextField(
            title = "Username",
            text = "testing test",
            textAccept = "Accept",
            textCancel = "Cancel"
        ) {

        }
    }
}