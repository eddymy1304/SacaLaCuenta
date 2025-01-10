package com.eddymy1304.sacalacuenta.core.model

data class ReceiptDetailExport(
    var idReceipt: Int,
    var name: String,
    var paymentMethod: String,
    var date: String,
    var product: String,
    var quantity: Double,
    var price: Double,
    var totalByProduct: Double
)