package com.eddymy1304.sacalacuenta.core.model

data class Receipt(
    var id: Int = -1,
    var date: String = "",
    var dateTime: String = "",
    var numberItems: Int = -1,
    var title: String = "",
    var total: Double = 0.00,
    var paymentMethod: String = ""
)