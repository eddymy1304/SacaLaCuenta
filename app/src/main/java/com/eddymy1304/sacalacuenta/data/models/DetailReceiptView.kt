package com.eddymy1304.sacalacuenta.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.eddymy1304.sacalacuenta.data.db.DetailReceiptEntity


data class DetailReceiptView(
    var id: Int? = null,
    var idReceipt: Int? = null,
    var name: MutableState<String?> = mutableStateOf(null),
    var quantity: MutableState<Double?> = mutableStateOf(null),
    var price: MutableState<Double?> = mutableStateOf(null),
    var total: MutableState<Double?> = mutableStateOf(null),
    var textQuantity: MutableState<String?> = mutableStateOf(null),
    var textPrice: MutableState<String?> = mutableStateOf(null),
    var textTotal: MutableState<String?> = mutableStateOf(null),

    var itemLocked: MutableState<Boolean> = mutableStateOf(true)
) {
    constructor(response: DetailReceiptEntity) : this(
        id = response.id,
        idReceipt = response.idReceipt,
        name = mutableStateOf(response.name),
        quantity = mutableStateOf(response.quantity),
        price = mutableStateOf(response.price),
        total = mutableStateOf(response.total)
    )
}