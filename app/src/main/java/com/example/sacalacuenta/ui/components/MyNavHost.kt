package com.example.sacalacuenta.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sacalacuenta.MainViewModel
import com.example.sacalacuenta.data.models.Screen.ScreenCuenta
import com.example.sacalacuenta.data.models.Screen.ScreenHistorial
import com.example.sacalacuenta.data.models.Screen.ScreenSettings
import com.example.sacalacuenta.data.models.Screen.ScreenTicket
import com.example.sacalacuenta.ui.screens.CuentaScreen
import com.example.sacalacuenta.ui.screens.HistorialScreen
import com.example.sacalacuenta.ui.screens.SettingsScreen
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
        composable<ScreenCuenta> {
            CuentaScreen(navController = navController, viewModel = viewModel)
        }

        composable<ScreenHistorial> {
            HistorialScreen(navController = navController, viewModel = viewModel)
        }

        composable<ScreenTicket> {
            TicketScreen(navController = navController, viewModel = viewModel)
        }

        composable<ScreenSettings> {
            SettingsScreen(navController = navController, viewModel = viewModel)
        }
    }
}

