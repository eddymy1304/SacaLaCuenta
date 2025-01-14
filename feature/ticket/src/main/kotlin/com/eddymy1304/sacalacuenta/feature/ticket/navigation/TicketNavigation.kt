package com.eddymy1304.sacalacuenta.feature.ticket.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.eddymy1304.sacalacuenta.feature.ticket.TicketScreen as composableTicketScreen
import kotlinx.serialization.Serializable

@Serializable
data class TicketScreen(val id: Int)

fun NavController.navigateToTicketScreen(id: Int) = navigate(route = TicketScreen(id))

fun NavGraphBuilder.ticketScreen(
    configScreen: () -> Unit,
    navToReceiptScreen: () -> Unit
) {
    composable<TicketScreen> { backStackEntry ->
        val screen = backStackEntry.toRoute<TicketScreen>()
        composableTicketScreen(
            configScreen = configScreen,
            navToReceiptScreen = navToReceiptScreen,
            id = screen.id
        )
    }
}