package com.eddymy1304.sacalacuenta.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme
import com.eddymy1304.sacalacuenta.core.model.Receipt
import com.eddymy1304.sacalacuenta.core.ui.utils.Utils

@Composable
fun ItemHistory(
    modifier: Modifier = Modifier,
    receipt: Receipt,
    onClick: () -> Unit = {}
) {
    ConstraintLayout(modifier.clickable { onClick() }) {
        val (div, name, payment, total, date) = createRefs()
        Text(
            text = stringResource(
                id = R.string.text_name,
                receipt.title
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
                id = R.string.text_total,
                receipt.total
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
                id = R.string.text_date,
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
                id = R.string.text_payment_method,
                receipt.paymentMethod
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
            receipt = Receipt()
        )
    }
}
