package com.eddymy1304.sacalacuenta.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import com.eddymy1304.sacalacuenta.data.models.ReceiptDetailExport
import com.eddymy1304.sacalacuenta.data.models.ReceiptWithDetailView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

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

    fun formatDate(date: String?): String {
        if (date.isNullOrBlank()) return ""
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(date)!!
            format.format(calendar.time)
        } catch (e: Exception) {
            ""
        }
    }

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

    fun getTime(dateTime: String?): String {
        if (dateTime.isNullOrBlank()) return ""
        val datetimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return try {
            val date = datetimeFormat.parse(dateTime)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            timeFormat.format(calendar.time)
        } catch (e: Exception) {
            ""
        }
    }

    fun chanceDayFromDate(fecha: String, day: Int): String {
        val datetimeFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return try {
            val fechaFormat = datetimeFormat.parse(fecha)
            calendar.time = fechaFormat!!
            calendar.add(Calendar.DAY_OF_MONTH, day)
            datetimeFormat.format(calendar.time)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun calendarDateToLong(fecha: String?): Long {
        return if (fecha != null) {
            val datetimeFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fechaFormat = datetimeFormat.parse(fecha)
            val calendar = Calendar.getInstance()
            calendar.time = fechaFormat!!
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

    fun getTodayLong(): Long {
        return Calendar.getInstance().timeInMillis
    }

    fun convertUtcToLocal(utcTimestamp: Long): Long {
        val timeZoneUtc = TimeZone.getTimeZone("UTC")
        val timeZoneLocal = TimeZone.getDefault()
        val offset = timeZoneUtc.getOffset(utcTimestamp) - timeZoneLocal.getOffset(utcTimestamp)
        return utcTimestamp + offset
    }

    fun exportToCsv(context: Context, list: List<ReceiptWithDetailView>) {
        val listExport = list.flatMap { cuentaWithDetalle ->
            cuentaWithDetalle.listDetailReceipt.map { detalle ->
                ReceiptDetailExport(
                    idReceipt = cuentaWithDetalle.receipt.id ?: 0,
                    name = cuentaWithDetalle.receipt.title.value.orEmpty(),
                    paymentMethod = cuentaWithDetalle.receipt.paymentMethod.value.orEmpty(),
                    date = cuentaWithDetalle.receipt.date.orEmpty(),
                    product = detalle.name.value.orEmpty(),
                    quantity = detalle.quantity.value ?: 0.0,
                    price = detalle.price.value ?: 0.0,
                    totalByProduct = detalle.total.value ?: 0.0
                )
            }
        }

        val csvFileName = "tickets.csv"
        val outputDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val outputFile = File(outputDir, csvFileName)

        try {
            outputFile.bufferedWriter().use { out ->
                // Escribir encabezados CSV si es necesario
                out.write("IDCUENTA,NOMBRE,METODO DE PAGO, FECHA, PRODUCTO, CANTIDAD, PRECIO, TOTAL\n")
                // Escribir datos
                listExport.forEach { data ->
                    out.write("${data.idReceipt},${data.name},${data.paymentMethod},${data.date},${data.product},${data.quantity},${data.price},${data.totalByProduct}\n")
                }
            }
            Log.d("Eddycito", "exportToCsv: $outputFile")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("Eddycito", "exportToCsv: $e")
        }
    }
}