package com.eddymy1304.sacalacuenta.feature.receipt.util

import java.util.regex.Pattern

object Utils {
    fun filterNumberDecimal(inputText: String, maxIntegerPart: Int, maxDecimalPart: Int): String {

        val decimalRegex = "^(0|[1-9]\\d{0,${maxIntegerPart - 1}})(\\.\\d{0,${maxDecimalPart}})?$"
        val matcher = Pattern.compile(decimalRegex).matcher(inputText)

        if (matcher.matches()) {
            return inputText
        } else {
            val filteredText = inputText.filter { it.isDigit() || it == '.' }

            // Corroborar que solo haya un punto decimal y que no esté al inicio del texto
            val filterColon =
                if (filteredText.count { it == '.' } > 1 ||
                    (filteredText.length > 1 && filteredText.startsWith('.'))
                ) {
                    filteredText.replaceFirst(".", "")
                } else {
                    filteredText
                }

            val parts = filterColon.split(".")
            val integerPart = parts[0]
            val decimalPart = parts.getOrNull(1)

            val integerPartFilterZero = if (integerPart.startsWith("0")) {
                integerPart.take(1) + integerPart.drop(1).trimStart('0')
            } else {
                integerPart
            }

            val integerPartFinal = integerPartFilterZero.take(maxIntegerPart)

            val decimalPartFinal = decimalPart?.take(maxDecimalPart)

            val finalText = "$integerPartFinal${decimalPartFinal?.let { ".$it" } ?: ""}"

            return finalText
        }
    }
}