package com.eddymy1304.sacalacuenta.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.eddymy1304.sacalacuenta.data.db.DetalleCuentaEntity


data class DetalleCuentaView(
    var id: Int? = null,
    var idCuenta: Int? = null,
    var name: MutableState<String?> = mutableStateOf(null),
    var quantity: MutableState<Double?> = mutableStateOf(null),
    var price: MutableState<Double?> = mutableStateOf(null),
    var total: MutableState<Double?> = mutableStateOf(null),
    var textQuantity: MutableState<String?> = mutableStateOf(null),
    var textPrice: MutableState<String?> = mutableStateOf(null),
    var textTotal: MutableState<String?> = mutableStateOf(null),

    var itemLocked: MutableState<Boolean> = mutableStateOf(true)
) {
    constructor(response: DetalleCuentaEntity) : this(
        id = response.id,
        idCuenta = response.idCuenta,
        name = mutableStateOf(response.name),
        quantity = mutableStateOf(response.quantity),
        price = mutableStateOf(response.price),
        total = mutableStateOf(response.total)
    )
}