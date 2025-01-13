package com.eddymy1304.sacalacuenta.core.model

data class DetailReceipt(
    var id: Int = -1,
    var idReceipt: Int = -1,
    var name: String = "",
    var amount: Double = 0.00,
    var price: Double = 0.00,
    var total: Double = 0.00,

    var itemLocked: Boolean = true
)