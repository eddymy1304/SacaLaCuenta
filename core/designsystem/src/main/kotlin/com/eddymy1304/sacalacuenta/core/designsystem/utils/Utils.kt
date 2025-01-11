package com.eddymy1304.sacalacuenta.core.designsystem.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

internal object Utils {
    fun chanceDayFromDate(date: String, day: Int): String {
        val datetimeFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return try {
            val fechaFormat = datetimeFormat.parse(date)
            calendar.time = fechaFormat!!
            calendar.add(Calendar.DAY_OF_MONTH, day)
            datetimeFormat.format(calendar.time)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun calendarDateToLong(date: String?): Long {
        return if (date != null) {
            val datetimeFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateFormat = datetimeFormat.parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat!!
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            Log.d("Eddycito", "calendarDateToLong: $calendar")
            calendar.timeInMillis
        } else {
            getTodayLong()
        }
    }

    private fun getTodayLong(): Long {
        return Calendar.getInstance().timeInMillis
    }

    fun convertUtcToLocal(utcTimestamp: Long): Long {
        val timeZoneUtc = TimeZone.getTimeZone("UTC")
        val timeZoneLocal = TimeZone.getDefault()
        val offset = timeZoneUtc.getOffset(utcTimestamp) - timeZoneLocal.getOffset(utcTimestamp)
        return utcTimestamp + offset
    }
}