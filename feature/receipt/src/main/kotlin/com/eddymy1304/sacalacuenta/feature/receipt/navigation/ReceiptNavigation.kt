package com.eddymy1304.sacalacuenta.feature.receipt.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.eddymy1304.sacalacuenta.feature.receipt.ReceiptScreen
import kotlinx.serialization.Serializable

@Serializable
data object ReceiptScreen

fun NavController.navigateToReceiptScreen(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = ReceiptScreen) {
        navOptions()
    }

fun NavGraphBuilder.receiptScreen(
    configScreen: () -> Unit,
    navToTicketScreen: (id: Int) -> Unit
) {
    composable<ReceiptScreen> {
        ReceiptScreen(
            configScreen = configScreen,
            navToTicketScreen = navToTicketScreen
        )
    }
}