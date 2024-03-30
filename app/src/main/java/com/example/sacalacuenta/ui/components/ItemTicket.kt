package com.example.sacalacuenta.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sacalacuenta.R
import com.example.sacalacuenta.data.models.DetalleCuentaView
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun ItemTicket(
    modifier: Modifier = Modifier,
    det: DetalleCuentaView
) {
    Row(modifier) {
        Text(
            modifier = Modifier
                .weight(4f)
                .padding(horizontal = 4.dp),
            text = det.name.value ?: "-",
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 4.dp),
            text = if (det.quantity.value == null) "-"
            else stringResource(
                id = R.string.number_two_decimal,
                det.quantity.value ?: 0.0
            ),
            textAlign = TextAlign.End
        )
        Text(
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 4.dp),
            text = if (det.price.value == null) "-"
            else stringResource(
                id = R.string.number_two_decimal,
                det.price.value ?: 0.0
            ),
            textAlign = TextAlign.End
        )
        Text(
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 4.dp),
            text = if (det.total.value == null) "-"
            else stringResource(
                id = R.string.number_two_decimal,
                det.total.value ?: 0.0
            ),
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true, locale = "es")
@Composable
fun PreviewItemTicket() {
    SacaLaCuentaTheme {
        ItemTicket(
            modifier = Modifier.fillMaxWidth(),
            det = DetalleCuentaView()
        )
    }
}