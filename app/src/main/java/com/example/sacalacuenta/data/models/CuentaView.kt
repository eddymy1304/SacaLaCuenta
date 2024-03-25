package com.example.sacalacuenta.data.models

data class CuentaView(
    var id: Int? = null,
    var title: String? = null,
    var regDate: String? = null,
    var total: Double? = null,
    var numberItems: Int? = null,
    var paymentMethod: String? = null,
)