package com.example.sacalacuenta.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun MySimpleLoading(
    modifier: Modifier = Modifier,
    onDismis: () -> Unit
) {
    Dialog(onDismissRequest = onDismis) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MySimpleLoadingPreview() {
    SacaLaCuentaTheme {
        MySimpleLoading(modifier = Modifier.fillMaxSize()) {

        }
    }
}