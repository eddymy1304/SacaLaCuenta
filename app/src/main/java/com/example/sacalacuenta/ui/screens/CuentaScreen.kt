package com.example.sacalacuenta.ui.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sacalacuenta.MainViewModel
import com.example.sacalacuenta.R
import com.example.sacalacuenta.ui.components.DetalleCuentaCard
import com.example.sacalacuenta.ui.components.MenuMetodoPago
import com.example.sacalacuenta.ui.components.getItemsMetodoPago
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme
import kotlinx.coroutines.launch

@Composable
fun CuentaScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val focus = remember { FocusRequester() }

    val cuenta by viewModel.cuenta.collectAsState()
    val listDetCuenta by viewModel.listDetCuenta.collectAsState()
    val total by viewModel.total.collectAsState()

    val maxLenghTitle = 20
    var textTitle by remember { mutableStateOf(cuenta.title.orEmpty()) }
    var textMetodoPago by remember { mutableStateOf(cuenta.paymentMethod.orEmpty()) }

    var openMetodoPago by remember { mutableStateOf(false) }

    Log.d(
        "CuentaScreen", """
        cuenta: $cuenta
        listDetCuenta: $listDetCuenta
        total: $total
    """.trimIndent()
    )

    ConstraintLayout(modifier = modifier) {
        val (titleCuenta, listDet, fab, metodoPago, totalCuenta, btnSave, divider) = createRefs()

        ExtendedFloatingActionButton(
            modifier = Modifier.constrainAs(btnSave) {
                bottom.linkTo(divider.top, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
            },
            icon = { Icon(imageVector = Icons.Outlined.Check, contentDescription = null) },
            text = { Text(text = stringResource(id = R.string.save)) },
            onClick = { viewModel.saveCuentaAndListDetCuenta(cuenta, listDetCuenta) })

        OutlinedTextField(
            modifier = Modifier
                .constrainAs(titleCuenta) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 8.dp)
                    width = Dimension.value(240.dp)
                }
                .focusRequester(focus),
            singleLine = true,
            value = textTitle,
            onValueChange = {
                if (it.length <= maxLenghTitle) {
                    textTitle = it
                    cuenta.title = textTitle
                    viewModel.updateCuenta(cuenta)
                }
            },
            label = {
                Text(
                    text = stringResource(id = R.string.label_title_cuenta),
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Cursive
            )
        )

        Column(modifier = Modifier.constrainAs(metodoPago) {
            top.linkTo(titleCuenta.bottom, margin = 8.dp)
            start.linkTo(parent.start, margin = 8.dp)
            width = Dimension.value(200.dp)
        }) {
            OutlinedTextField(
                value = textMetodoPago,
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                label = {
                    Text(
                        text = stringResource(id = R.string.label_title_metodo_pago),
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                interactionSource = remember {
                    MutableInteractionSource()
                }.also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect { interaction ->
                            if (interaction is PressInteraction.Release) {
                                openMetodoPago = true
                            }
                        }
                    }
                }
            )

            MenuMetodoPago(
                modifier = Modifier.width(200.dp),
                expended = openMetodoPago,
                onDismiss = { openMetodoPago = false },
                list = getItemsMetodoPago()
            ) {
                textMetodoPago = it
                cuenta.paymentMethod = textMetodoPago
                viewModel.updateCuenta(cuenta)
                openMetodoPago = false
            }
        }

        Text(
            modifier = Modifier.constrainAs(totalCuenta) {
                top.linkTo(parent.top, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
            },
            text = stringResource(id = R.string.text_total_cuenta, total),
            fontSize = 24.sp
        )

        HorizontalDivider(modifier = Modifier.constrainAs(divider) {
            top.linkTo(metodoPago.bottom, margin = 8.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        LazyColumn(
            state = listState,
            modifier = Modifier.constrainAs(listDet) {
                top.linkTo(metodoPago.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            itemsIndexed(listDetCuenta) { position, det ->
                DetalleCuentaCard(
                    position = position + 1,
                    detalleCuenta = det,
                    onValueChangeProduct = { viewModel.updateCurrentDetCuenta(position, it) },
                ) {
                    viewModel.deleteDetalleCuenta(det)
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier.constrainAs(fab) {
                bottom.linkTo(parent.bottom, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
            },
            onClick = {
                viewModel.addDetalleCuenta()
                scope.launch {
                    listState.animateScrollToItem(listDetCuenta.size)
                }
            }) {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, locale = "es",
    backgroundColor = 0xFFFFFFFF, device = "id:pixel_5"
)
@Composable
fun PreviewCuentaScreen(
) {
    SacaLaCuentaTheme {
        CuentaScreen(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController(), viewModel = hiltViewModel()
        )
    }
}