package com.example.sacalacuenta.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun MenuMetodoPago(
    modifier: Modifier = Modifier,
    expended: Boolean,
    onDismiss: () -> Unit,
    list: List<String>,
    onClick: (String) -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expended,
        onDismissRequest = onDismiss
    ) {
        list.forEach { text ->
            ItemMenuMetodoPago(text = text) {
                onClick(text)
            }
        }
    }
}

@Composable
fun ItemMenuMetodoPago(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        modifier = modifier,
        text = {
            Text(
                text = text,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        },
        onClick = onClick
    )
    HorizontalDivider()
}

@Preview(showBackground = true)
@Composable
fun PreviewMenuMetodoPago() {
    SacaLaCuentaTheme {
        MenuMetodoPago(expended = true, onDismiss = {}, list = getItemsMetodoPago(), onClick = {})
    }
}

fun getItemsMetodoPago(): List<String> {
    return listOf(
        "",
        "Efectivo",
        "Yape",
        "Plin",
        "Tarjeta",
        "Transferencia",
        "Agora"
    )
}