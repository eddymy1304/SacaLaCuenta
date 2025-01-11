package com.eddymy1304.sacalacuenta.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme

@Composable
fun CabListDetTicket(
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified
) {
    Row(modifier) {
        Text(
            color = textColor,
            modifier = Modifier.weight(4f),
            text = stringResource(id = R.string.label_title_product)
        )
        Text(
            color = textColor,
            modifier = Modifier.weight(2f),
            text = stringResource(id = R.string.label_title_cant)
        )
        Text(
            color = textColor,
            modifier = Modifier.weight(2f),
            text = stringResource(id = R.string.label_title_uni)
        )
        Text(
            color = textColor,
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