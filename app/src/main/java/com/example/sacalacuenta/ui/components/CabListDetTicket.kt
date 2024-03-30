package com.example.sacalacuenta.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sacalacuenta.R
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun CabListDetTicket(
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Text(
            modifier = Modifier.weight(4f),
            text = stringResource(id = R.string.label_title_producto)
        )
        Text(
            modifier = Modifier.weight(2f),
            text = stringResource(id = R.string.label_title_cant)
        )
        Text(
            modifier = Modifier.weight(2f),
            text = stringResource(id = R.string.label_title_Uni)
        )
        Text(
            modifier = Modifier.weight(2f),
            text = stringResource(id = R.string.label_title_total)
        )
    }
}

@Preview(showBackground = true, locale = "es")
@Composable
fun CabListDetTicketPreview() {
    SacaLaCuentaTheme {
        CabListDetTicket(Modifier.fillMaxWidth())
    }
}