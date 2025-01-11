package com.eddymy1304.sacalacuenta.core.ui

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.eddymy1304.sacalacuenta.core.designsystem.icon.AppIcons
import kotlinx.serialization.Serializable


@Serializable
sealed class Screen(
    @StringRes val title: Int = R.string.title_receipt
) {

    val icon: ImageVector
        get() = when (this) {
            is ScreenHistory -> AppIcons.History
            else -> AppIcons.Receipt
        }

    @Serializable
    data object ScreenReceipt : Screen(title = R.string.title_receipt)

    @Serializable
    data object ScreenHistory : Screen(title = R.string.title_history)

    @Serializable
    data class ScreenTicket(val id: Int) : Screen(title = R.string.title_ticket)

    @Serializable
    data object ScreenSettings : Screen(title = R.string.title_settings)

}

fun getListItemsBottomNav(): List<Screen> = listOf(Screen.ScreenReceipt, Screen.ScreenHistory)