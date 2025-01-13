package com.eddymy1304.sacalacuenta.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme
import com.eddymy1304.sacalacuenta.core.model.DetailReceipt

@Composable
fun ItemTicket(
    modifier: Modifier = Modifier,
    det: DetailReceipt,
    textColor: Color = Color.Unspecified
) {
    Row(modifier) {
        Text(
            color = textColor,
            modifier = Modifier
                .weight(4f)
                .padding(horizontal = 4.dp),
            text = det.name.ifBlank { "-" },
            textAlign = TextAlign.Start
        )
        Text(
            color = textColor,
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 4.dp),
            text = if (det.amount == 0.00) "-"
            else stringResource(
                id = R.string.number_two_decimal,
                det.amount
            ),
            textAlign = TextAlign.End
        )
        Text(
            color = textColor,
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 4.dp),
            text = if (det.price == 0.00) "-"
            else stringResource(
                id = R.string.number_two_decimal,
                det.price
            ),
            textAlign = TextAlign.End
        )
        Text(
            color = textColor,
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 4.dp),
            text = if (det.total == 0.00) "-"
            else stringResource(
                id = R.string.number_two_decimal,
                det.total
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
            det = DetailReceipt()
        )
    }
}