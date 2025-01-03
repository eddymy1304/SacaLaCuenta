package com.eddymy1304.sacalacuenta.ui.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.eddymy1304.sacalacuenta.MainViewModel
import com.eddymy1304.sacalacuenta.data.models.Screen
import com.eddymy1304.sacalacuenta.data.models.Screen.ScreenReceipt
import com.eddymy1304.sacalacuenta.data.models.Screen.ScreenHistory
import com.eddymy1304.sacalacuenta.data.models.Screen.ScreenSettings
import com.eddymy1304.sacalacuenta.data.models.Screen.ScreenTicket
import com.eddymy1304.sacalacuenta.ui.screens.ReceiptScreen
import com.eddymy1304.sacalacuenta.ui.screens.HistoryScreen
import com.eddymy1304.sacalacuenta.ui.screens.SettingsScreen
import com.eddymy1304.sacalacuenta.ui.screens.TicketScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavHostController,
    startDestination: Screen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<ScreenReceipt> {
            ReceiptScreen(navController = navController) {
                viewModel.configScreen(ScreenReceipt.title)
            }
        }

        composable<ScreenHistory> {
            HistoryScreen(navController = navController) {
                viewModel.configScreen(
                    title = ScreenHistory.title,
                    showActions = false
                )
            }
        }

        composable<ScreenTicket> { backStackEntry ->
            val screenTicket: ScreenTicket = backStackEntry.toRoute()
            Log.d("AppNavHost", "ScreenTicket id: ${screenTicket.id}")
            TicketScreen(
                navController = navController,
                id = screenTicket.id
            ) {
                viewModel.configScreen(
                    title = screenTicket.title,
                    showActions = false,
                    showBottomNav = false
                )
            }
        }

        composable<ScreenSettings> {
            SettingsScreen(navController = navController) {
                viewModel.configScreen(
                    title = ScreenSettings.title,
                    showActions = false,
                    showBottomNav = false
                )
            }
        }
    }
}

