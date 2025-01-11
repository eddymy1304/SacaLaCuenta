package com.eddymy1304.sacalacuenta.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.eddymy1304.sacalacuenta.core.designsystem.icon.AppIcons
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme
import com.eddymy1304.sacalacuenta.core.model.DetailReceipt
import java.util.regex.Pattern

@Composable
fun DetailReceiptCard(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    position: Int,
    det: DetailReceipt,
    onValueChangeName: (String) -> Unit,
    onValueChangeAmount: (String) -> Unit,
    onValueChangePrice: (String) -> Unit,
    onValueChangeTotal: (String) -> Unit,
    onClickIconLock: () -> Unit,
    onClickIconDelete: () -> Unit
) {

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
                    id = R.string.text_number_detail_receipt,
                    position
                ),
                modifier = Modifier.constrainAs(number) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top, margin = 8.dp)
                },
                fontSize = 15.sp
            )

            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                value = det.name,
                singleLine = true,
                onValueChange = onValueChangeName,
                modifier = Modifier
                    .constrainAs(name) {
                        start.linkTo(number.end)
                        top.linkTo(parent.top, margin = 8.dp)
                        width = Dimension.value(200.dp)
                    }
                    .focusRequester(focusRequester),
                label = {
                    Text(text = stringResource(id = R.string.label_title_product))
                },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(quantity) {
                        start.linkTo(parent.start)
                        top.linkTo(name.bottom)
                        end.linkTo(price.start)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        width = Dimension.value(120.dp)
                    },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End,
                    fontSize = 15.sp
                ),
                enabled = !det.itemLocked,
                label = {
                    Text(text = stringResource(id = R.string.label_title_amount))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = "${det.quantity}",
                onValueChange = onValueChangeAmount
                /*{
                    det.textQuantity.value = filterNumberDecimal(it, 4, 2)
                    det.quantity.value = try {
                        det.textQuantity.value?.toDoubleOrNull()
                    } catch (e: Exception) {
                        null
                    }
                    if (det.price.value != null) {
                        val res = try {
                            (det.textQuantity.value?.toDouble() ?: 0.0) * (det.price.value ?: 0.0)
                        } catch (e: Exception) {
                            0.00
                        }
                        det.textTotal.value = try {
                            String.format(Locale.getDefault(), "%.2f", res)
                        } catch (e: Exception) {
                            ""
                        }
                        det.total.value = det.textTotal.value?.toDoubleOrNull()
                    }
                    onValueChangeProduct()
                }*/,
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(price) {
                        start.linkTo(quantity.end)
                        top.linkTo(name.bottom)
                        end.linkTo(total.start)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        width = Dimension.value(90.dp)
                    },
                enabled = !det.itemLocked,
                value = "${det.price}",
                onValueChange = onValueChangePrice /*{
                    det.textPrice.value = filterNumberDecimal(it, 4, 2)
                    det.price.value = try {
                        det.textPrice.value?.toDoubleOrNull()
                    } catch (e: Exception) {
                        null
                    }
                    if (det.quantity.value != null) {
                        val res = try {
                            (det.quantity.value ?: 0.0) * (det.textPrice.value?.toDouble() ?: 0.0)
                        } catch (e: Exception) {
                            0.00
                        }

                        det.textTotal.value = try {
                            String.format(Locale.getDefault(), "%.2f", res)
                        } catch (e: Exception) {
                            "0.00"
                        }
                        det.total.value = det.textTotal.value?.toDoubleOrNull()
                    }
                    onValueChangeProduct()
                }*/,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = {
                    Text(text = stringResource(id = R.string.label_title_price))
                },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End,
                    fontSize = 15.sp
                )
            )
            OutlinedTextField(
                value = "${det.total}",
                onValueChange = onValueChangeTotal, /*{
                    det.textTotal.value = filterNumberDecimal(it, 4, 2)
                    det.total.value = try {
                        det.textTotal.value?.toDoubleOrNull()
                    } catch (e: Exception) {
                        null
                    }
                    onValueChangeProduct()
                },*/
                singleLine = true,
                enabled = det.itemLocked,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .constrainAs(total) {
                        start.linkTo(price.end)
                        top.linkTo(name.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        width = Dimension.value(90.dp)
                    },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End,
                    fontSize = 15.sp
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
                onClick = onClickIconDelete
            ) {
                Icon(
                    imageVector = AppIcons.Trash,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = null
                )
            }

            IconButton(
                modifier = Modifier.constrainAs(lockIcon) {
                    end.linkTo(deleteIcon.start)
                    top.linkTo(parent.top)
                },
                onClick = onClickIconLock/*{
                    det.itemLocked.value = !det.itemLocked.value
                    det.quantity.value = null
                    det.price.value = null
                    det.total.value = null
                    det.textQuantity.value = null
                    det.textPrice.value = null
                    det.textTotal.value = null
                    onValueChangeProduct()
                }*/
            ) {
                Icon(
                    imageVector = if (det.itemLocked) AppIcons.Lock
                    else AppIcons.Unlocked,
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

        val parts = filterColon.split(".")
        val integerPart = parts[0]
        val decimalPart = parts.getOrNull(1)

        val integerPartFilterZero = if (integerPart.startsWith("0")) {
            integerPart.take(1) + integerPart.drop(1).trimStart('0')
        } else {
            integerPart
        }

        val integerPartFinal = integerPartFilterZero.take(maxIntegerPart)

        val decimalPartFinal = decimalPart?.take(maxDecimalPart)

        val finalText = "$integerPartFinal${decimalPartFinal?.let { ".$it" } ?: ""}"

        return finalText
    }
}

@Preview(showBackground = true, locale = "es")
@Composable
fun PreviewDetailCard() {
    SacaLaCuentaTheme {
        DetailReceiptCard(
            modifier = Modifier.fillMaxWidth(),
            det = DetailReceipt(
                name = "Chicken",
                quantity = 0.00,
                price = 0.00,
                total = 0.00,
            ),
            position = 2,
            focusRequester = remember { FocusRequester() },
            onClickIconDelete = {},
            onValueChangeName = { },
            onValueChangeAmount = { },
            onValueChangePrice = { },
            onValueChangeTotal = { },
            onClickIconLock = { },
        )
    }
}