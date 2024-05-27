package com.example.sacalacuenta.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.sacalacuenta.MainViewModel
import com.example.sacalacuenta.data.models.ScreenCuenta
import com.example.sacalacuenta.data.models.ScreenHistorial
import com.example.sacalacuenta.data.models.ScreenTicket
import com.example.sacalacuenta.ui.screens.CuentaScreen
import com.example.sacalacuenta.ui.screens.HistorialScreen
import com.example.sacalacuenta.ui.screens.TicketScreen

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<ScreenCuenta> { backStackEntry ->
            val prueba = backStackEntry.toRoute<ScreenCuenta>()
            CuentaScreen(navController = navController, viewModel = viewModel)
        }

        composable<ScreenHistorial> {
            HistorialScreen(navController = navController, viewModel = viewModel)
        }

        composable<ScreenTicket> {
            TicketScreen(navController = navController, viewModel = viewModel)
        }
    }
}

