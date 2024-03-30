package com.example.sacalacuenta.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {

    fun getDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }

    fun getDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return dateFormat.format(calendar.time)
    }

    fun formatDateTime(dateTime: String?): String {
        if (dateTime.isNullOrBlank()) return ""
        val datetimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val format = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault())
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