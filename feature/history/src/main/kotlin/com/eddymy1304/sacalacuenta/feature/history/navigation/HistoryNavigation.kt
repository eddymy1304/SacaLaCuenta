package com.eddymy1304.sacalacuenta.feature.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.eddymy1304.sacalacuenta.feature.history.HistoryScreen
import kotlinx.serialization.Serializable

@Serializable
data object HistoryScreen

fun NavController.navigateToHistoryScreen(navOptions: NavOptionsBuilder.() -> Unit = {}) =
    navigate(route = HistoryScreen) {
        navOptions()
    }

fun NavGraphBuilder.historyScreen(
    configScreen: () -> Unit,
    navToTicketScreen: (id: Int) -> Unit
) {
    composable<HistoryScreen> {
        HistoryScreen(
            configScreen = configScreen,
            navToTicketScreen = navToTicketScreen
        )
    }
}