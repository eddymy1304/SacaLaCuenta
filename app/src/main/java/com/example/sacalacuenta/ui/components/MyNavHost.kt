package com.example.sacalacuenta.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sacalacuenta.MainViewModel
import com.example.sacalacuenta.data.models.Screen
import com.example.sacalacuenta.ui.screens.CuentaScreen
import com.example.sacalacuenta.ui.screens.HistorialScreen
import com.example.sacalacuenta.ui.screens.TicketScreen

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.CuentaScreen.route) {
            CuentaScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Screen.HistorialScreen.route) {
            HistorialScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Screen.TicketScreen.route) {
            TicketScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}