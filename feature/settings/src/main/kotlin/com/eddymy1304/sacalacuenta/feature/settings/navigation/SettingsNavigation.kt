package com.eddymy1304.sacalacuenta.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.eddymy1304.sacalacuenta.feature.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreen

fun NavController.navigateToSettingsScreen() = navigate(route = SettingsScreen)

fun NavGraphBuilder.settingsScreen(
    configScreen: () -> Unit,
) {
    composable<SettingsScreen> {
        SettingsScreen(configScreen = configScreen)
    }
}