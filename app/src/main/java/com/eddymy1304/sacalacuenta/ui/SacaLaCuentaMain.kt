package com.eddymy1304.sacalacuenta.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.eddymy1304.sacalacuenta.MainViewModel
import com.eddymy1304.sacalacuenta.ui.screens.MainScreen
import com.eddymy1304.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun SacaLaCuentaMain(
    viewModel : MainViewModel = hiltViewModel()
) {
    SacaLaCuentaTheme {
        MainScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = viewModel
        )
    }
}