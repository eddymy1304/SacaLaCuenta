package com.eddymy1304.sacalacuenta.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.eddymy1304.sacalacuenta.R
import com.eddymy1304.sacalacuenta.data.models.ReceiptView
import com.eddymy1304.sacalacuenta.ui.theme.SacaLaCuentaTheme
import com.eddymy1304.sacalacuenta.utils.Utils

@Composable
fun ItemHistory(
    modifier: Modifier = Modifier,
    receipt: ReceiptView,
    onClick: () -> Unit = {}
) {
    ConstraintLayout(modifier.clickable { onClick() }) {
        val (div, name, payment, total, date) = createRefs()
        Text(
            text = stringResource(
                id = R.string.text_nombre,
                receipt.title.value.orEmpty()
            ),
            modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = stringResource(
                id = R.string.text_total_cuenta,
                receipt.total.value ?: 0.00
            ),
            modifier = Modifier.constrainAs(total) {
                start.linkTo(date.end)
                end.linkTo(parent.end)
                top.linkTo(payment.bottom)
                width = Dimension.wrapContent
            }
        )

        Text(
            text = stringResource(
                id = R.string.text_fecha,
                Utils.formatDateTime(receipt.dateTime)
            ),
            modifier = Modifier.constrainAs(date) {
                start.linkTo(parent.start)
                end.linkTo(total.start)
                top.linkTo(payment.bottom)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = stringResource(
                id = R.string.text_metodo_pago,
                receipt.paymentMethod.value.orEmpty()
            ),
            modifier = Modifier.constrainAs(payment) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(name.bottom)
                width = Dimension.fillToConstraints
            }
        )
        HorizontalDivider(
            modifier = Modifier.constrainAs(div) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true, locale = "es")
@Composable
fun PreviewItemHistory() {
    SacaLaCuentaTheme {
        ItemHistory(
            modifier = Modifier.fillMaxWidth(),
            receipt = ReceiptView(
                title = remember {
                    mutableStateOf("Receipt 1")
                },
                total = remember {
                    mutableStateOf(100.00)
                },
                paymentMethod = remember {
                    mutableStateOf("Credit card")
                },
                dateTime = Utils.formatDateTime(Utils.getDateTime())
            )
        )
    }
}
