package com.eddymy1304.sacalacuenta.data.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.ui.graphics.vector.ImageVector
import com.eddymy1304.sacalacuenta.R
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

//@Serializable
//data object ScreenCuenta {
//    val title = R.string.cuenta
//    val icon = Icons.Outlined.Receipt
//}
//
//@Serializable
//data object ScreenHistorial {
//    val title = R.string.historial
//    val icon = Icons.Outlined.CalendarMonth
//}
//
//@Serializable
//data object ScreenTicket {
//    val title = R.string.ticket
//    val icon = Icons.Outlined.Receipt
//}

@Serializable
sealed class Screen(
    @StringRes val title: Int = R.string.cuenta,
    @Contextual val icon: ImageVector = Icons.Outlined.Receipt
) {

    @Serializable
    data object ScreenCuenta : Screen(
        title = R.string.cuenta,
        icon = Icons.Outlined.Receipt
    )

    @Serializable
    data object ScreenHistorial : Screen(
        title = R.string.historial,
        icon = Icons.Outlined.CalendarMonth
    )

    @Serializable
    data object ScreenTicket: Screen(
        title = R.string.ticket,
        icon = Icons.Outlined.Receipt
    )

    @Serializable
    data object ScreenSettings: Screen(
        title = R.string.settings,
        icon = Icons.Outlined.Receipt
    )

}

fun getListItemsBottomNav(): List<Screen> = listOf(Screen.ScreenCuenta, Screen.ScreenHistorial)