package com.example.sacalacuenta.data.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ListAlt
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.sacalacuenta.R
import com.example.sacalacuenta.data.models.Screen.*

sealed class Screen(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector? = null
) {
    data object CuentaScreen : Screen(
        route = "cuenta_screen",
        title = R.string.cuenta,
        icon = Icons.Outlined.Receipt
    )

    data object HistorialScreen : Screen(
        route = "historial_screen",
        title = R.string.historial,
        icon = Icons.Outlined.CalendarMonth
    )
}

fun getListItemsBottomNav() = listOf(CuentaScreen, HistorialScreen)