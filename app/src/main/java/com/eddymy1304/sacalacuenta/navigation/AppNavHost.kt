package com.eddymy1304.sacalacuenta.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.eddymy1304.sacalacuenta.MainViewModel
import com.eddymy1304.sacalacuenta.feature.history.navigation.historyScreen
import com.eddymy1304.sacalacuenta.feature.receipt.navigation.ReceiptScreen
import com.eddymy1304.sacalacuenta.feature.receipt.navigation.navigateToReceiptScreen
import com.eddymy1304.sacalacuenta.feature.receipt.navigation.receiptScreen
import com.eddymy1304.sacalacuenta.feature.settings.navigation.settingsScreen
import com.eddymy1304.sacalacuenta.feature.ticket.navigation.navigateToTicketScreen
import com.eddymy1304.sacalacuenta.feature.ticket.navigation.ticketScreen
import com.eddymy1304.sacalacuenta.navigation.TopLevelDestination.*

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: TopLevelDestination,
    viewModel: MainViewModel
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        receiptScreen(
            configScreen = { viewModel.configScreen(title = RECEIPT.title) },
            navToTicketScreen = navController::navigateToTicketScreen
        )

        historyScreen(
            configScreen = { viewModel.configScreen(title = HISTORY.title, showActions = false) },
            navToTicketScreen = navController::navigateToTicketScreen
        )

        settingsScreen {
            viewModel.configScreen(
                title = SETTINGS.title,
                showActions = false,
                showBottomNav = false
            )
        }

        ticketScreen(
            configScreen = {
                viewModel.configScreen(
                    title = TICKET.title,
                    showBottomNav = false,
                    showActions = false
                )
            },
            navToReceiptScreen = {
                navController.navigateToReceiptScreen {
                    popUpTo(ReceiptScreen) {
                        inclusive = true
                    }
                }
            }
        )

    }

}