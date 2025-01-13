package com.eddymy1304.sacalacuenta.core.designsystem.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme

@Composable
fun SimpleMenu(
    modifier: Modifier = Modifier,
    expended: Boolean,
    onDismiss: () -> Unit,
    list: List<String>,
    onClick: (String) -> Unit
) {
    DropdownMenu(
        modifier = modifier, expanded = expended, onDismissRequest = onDismiss
    ) {
        list.forEach { text ->
            ItemMenu(text = text) {
                onClick(text)
            }
        }
    }
}

@Composable
fun ItemMenu(
    modifier: Modifier = Modifier, text: String, onClick: () -> Unit
) {
    DropdownMenuItem(
        modifier = modifier, text = {
            Text(
                text = text, fontSize = 14.sp, textAlign = TextAlign.Center
            )
        }, onClick = onClick
    )
    HorizontalDivider()
}

@Preview(showBackground = true, device = "id:pixel_6")
@Composable
fun PreviewSimpleMenu() {
    SacaLaCuentaTheme {
        SimpleMenu(expended = true, onDismiss = {}, list = getItemsPaymentMethodPreview(), onClick = {})
    }
}

fun getItemsPaymentMethodPreview(): List<String> {
    return listOf(
        "", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"
    )
}