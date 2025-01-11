package com.eddymy1304.sacalacuenta.core.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {
    fun formatDateTime(dateTime: String?): String {
        if (dateTime.isNullOrBlank()) return ""
        val datetimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return try {
            val date = datetimeFormat.parse(dateTime)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            format.format(calendar.time)
        } catch (e: Exception) {
            ""
        }
    }
}