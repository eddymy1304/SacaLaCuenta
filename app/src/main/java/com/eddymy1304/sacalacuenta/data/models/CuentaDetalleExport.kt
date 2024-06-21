package com.eddymy1304.sacalacuenta.data.models

data class CuentaDetalleExport(
    var idCuenta: Int,
    var name: String,
    var paymentMethod: String,
    var date: String,
    var product: String,
    var quantity: Double,
    var price: Double,
    var totalByProduct: Double
)