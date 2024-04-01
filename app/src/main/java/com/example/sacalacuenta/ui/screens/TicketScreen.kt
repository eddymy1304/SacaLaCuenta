package com.example.sacalacuenta.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalGroceryStore
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.example.sacalacuenta.MainViewModel
import com.example.sacalacuenta.R
import com.example.sacalacuenta.data.models.DetalleCuentaView
import com.example.sacalacuenta.ui.components.CabListDetTicket
import com.example.sacalacuenta.ui.components.ItemTicket
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun TicketScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {

    val cuentaWithDetalle by viewModel.cuentaWithDetalle.collectAsState()

    TicketScreen(
        modifier = modifier,
        textTitle = cuentaWithDetalle.cuenta.title.value.orEmpty(),
        textPaymentMethod = cuentaWithDetalle.cuenta.paymentMethod.value.orEmpty(),
        textDate = cuentaWithDetalle.cuenta.dateTime.orEmpty(),
        textTotal = cuentaWithDetalle.cuenta.total.value ?: 0.0,
        listDet = cuentaWithDetalle.listDetCuenta,
        onClickIconHome = { navController.navigateUp() },
        onClickIconShare = {

        }
    )
}

@Composable
fun TicketScreen(
    modifier: Modifier = Modifier,
    textTitle: String = "",
    textPaymentMethod: String = "",
    textDate: String = "",
    textTotal: Double = 0.0,
    listDet: List<DetalleCuentaView> = listOf(),
    onClickIconShare: () -> Unit = {},
    onClickIconHome: () -> Unit = {}
) {
    ConstraintLayout(modifier = modifier) {
        val (image, title, method, cab, list, total, date, div1, div2, iconShare, iconHome) = createRefs()

        Icon(
            imageVector = Icons.Outlined.LocalGroceryStore,
            contentDescription = null,
            modifier = Modifier.constrainAs(image) {
                top.linkTo(parent.top, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            },
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(id = R.string.text_nombre, textTitle),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            })

        Text(
            text = stringResource(id = R.string.text_metodo_pago, textPaymentMethod),
            modifier = Modifier.constrainAs(method) {
                top.linkTo(title.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = stringResource(id = R.string.text_fecha, textDate),
            modifier = Modifier.constrainAs(date) {
                top.linkTo(method.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        CabListDetTicket(modifier = Modifier.constrainAs(cab) {
            top.linkTo(date.bottom, margin = 8.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            width = Dimension.matchParent
        })

        HorizontalDivider(Modifier.constrainAs(div1) {
            top.linkTo(cab.bottom)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            width = Dimension.fillToConstraints
        })

        LazyColumn(modifier = Modifier.constrainAs(list) {
            top.linkTo(div1.bottom)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            bottom.linkTo(div2.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }) {
            items(listDet) {
                ItemTicket(det = it)
            }
        }

        HorizontalDivider(Modifier.constrainAs(div2) {
            top.linkTo(list.bottom)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            width = Dimension.fillToConstraints
        })

        Text(
            text = stringResource(id = R.string.text_total_cuenta, textTotal),
            modifier = Modifier.constrainAs(total) {
                top.linkTo(div2.bottom, margin = 8.dp)
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(iconShare.top, margin = 8.dp)
            }
        )

        IconButton(
            modifier = Modifier.constrainAs(iconShare) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(iconHome.start, margin = 8.dp)
            },
            onClick = onClickIconShare
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(
            modifier = Modifier.constrainAs(iconHome) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 8.dp)
                start.linkTo(iconShare.end, margin = 8.dp)
            },
            onClick = onClickIconHome
        ) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, locale = "es")
@Composable
fun PreviewTicketScreen() {
    SacaLaCuentaTheme {
        TicketScreen(
            modifier = Modifier.fillMaxSize(),
        )
    }
}