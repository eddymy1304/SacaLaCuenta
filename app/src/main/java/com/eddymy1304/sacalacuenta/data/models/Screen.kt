package com.eddymy1304.sacalacuenta.data.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.ui.graphics.vector.ImageVector
import com.eddymy1304.sacalacuenta.R
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(
    @StringRes val title: Int = R.string.cuenta
) {

    val icon: ImageVector
        get() = when (this) {
            is ScreenHistory -> Icons.Outlined.CalendarMonth
            else -> Icons.Outlined.Receipt
        }

    @Serializable
    data object ScreenReceipt : Screen(title = R.string.cuenta)

    @Serializable
    data object ScreenHistory : Screen(title = R.string.historial)

    @Serializable
    data class ScreenTicket(val id: Int) : Screen(title = R.string.ticket)

    @Serializable
    data object ScreenSettings : Screen(title = R.string.settings)

}

fun getListItemsBottomNav(): List<Screen> = listOf(Screen.ScreenReceipt, Screen.ScreenHistory)