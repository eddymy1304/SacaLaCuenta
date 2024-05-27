package com.example.sacalacuenta.data.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Receipt
import com.example.sacalacuenta.R
import kotlinx.serialization.Serializable

@Serializable
data object ScreenCuenta {
    val title = R.string.cuenta
    val icon = Icons.Outlined.Receipt
}

@Serializable
data object ScreenHistorial {
    val title = R.string.historial
    val icon = Icons.Outlined.CalendarMonth
}

@Serializable
data object ScreenTicket {
    val title = R.string.ticket
    val icon = Icons.Outlined.Receipt
}


fun getListItemsBottomNav() = listOf(ScreenCuenta, ScreenHistorial)