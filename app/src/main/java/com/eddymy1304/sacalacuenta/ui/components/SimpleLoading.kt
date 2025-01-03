package com.eddymy1304.sacalacuenta.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.eddymy1304.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun SimpleLoading(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleLoadingPreview() {
    SacaLaCuentaTheme {
        SimpleLoading(modifier = Modifier.fillMaxSize()) {

        }
    }
}