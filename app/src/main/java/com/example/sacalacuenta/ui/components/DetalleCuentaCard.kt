package com.example.sacalacuenta.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.sacalacuenta.R
import com.example.sacalacuenta.data.models.DetalleCuentaView
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme
import java.util.Locale
import java.util.regex.Pattern

@Composable
fun DetalleCuentaCard(
    modifier: Modifier = Modifier,
    position: Int,
    detalleCuenta: DetalleCuentaView,
    onValueChangeProduct: (DetalleCuentaView) -> Unit,
    onClickDelete: () -> Unit
) {

    val focus = remember { FocusRequester() }

    var textName by remember { mutableStateOf(detalleCuenta.name.orEmpty()) }
    var textQuantity by remember { mutableStateOf(detalleCuenta.quantity?.toString().orEmpty()) }
    var textPrice by remember { mutableStateOf(detalleCuenta.price?.toString().orEmpty()) }
    var textTotal by remember { mutableStateOf(detalleCuenta.total?.toString().orEmpty()) }
    var lockedTextField by remember { mutableStateOf(detalleCuenta.itemLocked) }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (number, name, quantity, price, total, deleteIcon, lockIcon) = createRefs()

            Text(
                text = stringResource(
                    id = R.string.text_number_detalle_cuenta,
                    position
                ),
                modifier = Modifier.constrainAs(number) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top, margin = 8.dp)
                },
                fontSize = 16.sp
            )

            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                value = textName,
                singleLine = true,
                onValueChange = {
                    textName = it
                    detalleCuenta.name = it
                    onValueChangeProduct(detalleCuenta)
                },
                modifier = Modifier
                    .constrainAs(name) {
                        start.linkTo(number.end)
                        top.linkTo(parent.top, margin = 8.dp)
                        width = Dimension.value(200.dp)
                    },
                label = {
                    Text(text = stringResource(id = R.string.label_title_producto))
                },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Cursive
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(quantity) {
                        start.linkTo(parent.start, margin = 8.dp)
                        top.linkTo(name.bottom)
                        end.linkTo(price.start, margin = 8.dp)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        width = Dimension.value(100.dp)
                    },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End,
                    fontSize = 16.sp
                ),
                enabled = !lockedTextField,
                label = {
                    Text(text = stringResource(id = R.string.label_title_cantidad))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = textQuantity,
                onValueChange = {
                    textQuantity = filterNumberDecimal(it, 4, 2)
                    detalleCuenta.quantity = try {
                        textQuantity.toDoubleOrNull()
                    } catch (e: Exception) {
                        null
                    }
                    if (textPrice.isNotBlank()) {
                        val res = try {
                            textQuantity.toDouble() * textPrice.toDouble()
                        } catch (e: Exception) {
                            0.00
                        }
                        textTotal = try {
                            String.format(Locale.getDefault(), "%.2f", res)
                        } catch (e: Exception) {
                            ""
                        }
                        detalleCuenta.total = textTotal.toDoubleOrNull()
                    }
                    onValueChangeProduct(detalleCuenta)
                },
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(price) {
                        start.linkTo(quantity.end)
                        top.linkTo(name.bottom)
                        end.linkTo(total.start)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        width = Dimension.value(100.dp)
                    },
                enabled = !lockedTextField,
                value = textPrice,
                onValueChange = {
                    textPrice = filterNumberDecimal(it, 4, 2)
                    detalleCuenta.price = try {
                        textPrice.toDoubleOrNull()
                    } catch (e: Exception) {
                        null
                    }
                    if (textQuantity.isNotBlank()) {
                        val res = try {
                            textQuantity.toDouble() * textPrice.toDouble()
                        } catch (e: Exception) {
                            0.00
                        }
                        textTotal = try {
                            String.format(Locale.getDefault(), "%.2f", res)
                        } catch (e: Exception) {
                            "0.00"
                        }
                        detalleCuenta.total = textTotal.toDoubleOrNull()
                    }
                    onValueChangeProduct(detalleCuenta)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = {
                    Text(text = stringResource(id = R.string.label_title_precio))
                },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End,
                    fontSize = 16.sp
                )
            )
            OutlinedTextField(
                value = textTotal,
                onValueChange = {
                    textTotal = filterNumberDecimal(it, 4, 2)
                    detalleCuenta.total = try {
                        textTotal.toDoubleOrNull()
                    } catch (e: Exception) {
                        null
                    }
                    onValueChangeProduct(detalleCuenta)
                },
                singleLine = true,
                enabled = lockedTextField,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .constrainAs(total) {
                        start.linkTo(price.end, margin = 8.dp)
                        top.linkTo(name.bottom)
                        end.linkTo(parent.end, margin = 8.dp)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        width = Dimension.value(100.dp)
                    }
                    .focusRequester(focus),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End,
                    fontSize = 16.sp
                ),
                label = {
                    Text(text = stringResource(id = R.string.label_title_total))
                }
            )

            IconButton(
                modifier = Modifier.constrainAs(deleteIcon) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
                onClick = onClickDelete
            ) {
                Icon(
                    imageVector = Icons.Outlined.DeleteOutline,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = null
                )
            }

            IconButton(
                modifier = Modifier.constrainAs(lockIcon) {
                    end.linkTo(deleteIcon.start)
                    top.linkTo(parent.top)
                },
                onClick = {
                    lockedTextField = !lockedTextField
                    textQuantity = ""
                    textPrice = ""
                    textTotal = ""
                    detalleCuenta.itemLocked = lockedTextField
                    detalleCuenta.quantity = null
                    detalleCuenta.price = null
                    detalleCuenta.total = null
                    onValueChangeProduct(detalleCuenta)
                }
            ) {
                Icon(
                    imageVector = if (lockedTextField) Icons.Outlined.Lock
                    else Icons.Outlined.LockOpen,
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    contentDescription = null
                )
            }


        }
        HorizontalDivider()
    }
}

fun filterNumberDecimal(inputText: String, maxIntegerPart: Int, maxDecimalPart: Int): String {

    val decimalRegex = "^(0|[1-9]\\d{0,${maxIntegerPart - 1}})(\\.\\d{0,${maxDecimalPart}})?$"
    val matcher = Pattern.compile(decimalRegex).matcher(inputText)

    if (matcher.matches()) {
        return inputText
    } else {
        val filteredText = inputText.filter { it.isDigit() || it == '.' }

        // Corroborar que solo haya un punto decimal y que no esté al inicio del texto
        val filterColon =
            if (filteredText.count { it == '.' } > 1 ||
                (filteredText.length > 1 && filteredText.startsWith('.'))
            ) {
                filteredText.replaceFirst(".", "")
            } else {
                filteredText
            }

        val partes = filterColon.split(".")
        val parteEntera = partes[0]
        val parteDecimal = partes.getOrNull(1)

        val parteEnteraFilterZero = if (parteEntera.startsWith("0")) {
            parteEntera.take(1) + parteEntera.drop(1).trimStart('0')
        } else {
            parteEntera
        }

        val parteEnteraFinal = parteEnteraFilterZero.take(maxIntegerPart)

        val parteDecimalFinal = parteDecimal?.take(maxDecimalPart)

        val finalText = "$parteEnteraFinal${parteDecimalFinal?.let { ".$it" } ?: ""}"

        return finalText
    }
}

@Preview(showBackground = true, locale = "es")
@Composable
fun PreviewDetalleCard() {
    SacaLaCuentaTheme {
        DetalleCuentaCard(
            modifier = Modifier.fillMaxWidth(),
            detalleCuenta = DetalleCuentaView(
                name = "Pollo",
                quantity = null,
                price = null,
                total = null,
            ),
            position = 2,
            onValueChangeProduct = {},
            onClickDelete = {},
        )
    }
}