package com.example.sacalacuenta.data.models


data class DetalleCuentaView(
    var id: Int? = null,
    var idCuenta: Int? = null,
    var name: String? = null,
    var quantity: Double? = null,
    var price: Double? = null,
    var total: Double? = null,

    var itemLocked: Boolean = true
)