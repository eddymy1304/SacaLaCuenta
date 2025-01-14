package com.eddymy1304.sacalacuenta.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.eddymy1304.sacalacuenta.core.designsystem.icon.AppIcons
import com.eddymy1304.sacalacuenta.feature.history.navigation.HistoryScreen
import com.eddymy1304.sacalacuenta.feature.receipt.navigation.ReceiptScreen
import com.eddymy1304.sacalacuenta.feature.settings.navigation.SettingsScreen
import com.eddymy1304.sacalacuenta.feature.ticket.navigation.TicketScreen
import com.eddymy1304.sacalacuenta.feature.receipt.R as receiptR
import com.eddymy1304.sacalacuenta.feature.history.R as historyR
import com.eddymy1304.sacalacuenta.feature.ticket.R as ticketR
import com.eddymy1304.sacalacuenta.feature.settings.R as settingsR
import kotlin.reflect.KClass

enum class TopLevelDestination(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: KClass<*>
) {
    RECEIPT(
        title = receiptR.string.title_receipt,
        icon = AppIcons.Receipt,
        route = ReceiptScreen::class
    ),
    HISTORY(
        title = historyR.string.title_history,
        icon = AppIcons.Receipt,
        route = HistoryScreen::class
    ),
    SETTINGS(
        title = settingsR.string.title_settings,
        icon = AppIcons.Settings,
        route = SettingsScreen::class
    ),
    TICKET(
        title = ticketR.string.title_ticket,
        icon = AppIcons.Cart,
        route = TicketScreen::class
    )
}