package com.eddymy1304.sacalacuenta.feature.history.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import com.eddymy1304.sacalacuenta.core.model.ReceiptDetailExport
import com.eddymy1304.sacalacuenta.core.model.ReceiptWithDetail
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {

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

    fun exportToCsv(context: Context, list: List<ReceiptWithDetail>) {
        val listExport = list.flatMap { receiptWithDetail ->
            receiptWithDetail.listDetailReceipt.map { detail ->
                ReceiptDetailExport(
                    idReceipt = receiptWithDetail.receipt.id,
                    name = receiptWithDetail.receipt.title,
                    paymentMethod = receiptWithDetail.receipt.paymentMethod,
                    date = receiptWithDetail.receipt.date,
                    product = detail.name,
                    quantity = detail.amount,
                    price = detail.price,
                    totalByProduct = detail.total
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
            Log.d("ExportToCsv", "Success exportToCsv: $outputFile")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("ExportToCsv", "Error exportToCsv: $e")
        }
    }
}