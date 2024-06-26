package com.eddymy1304.sacalacuenta.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.eddymy1304.sacalacuenta.data.db.CuentaEntity
import com.eddymy1304.sacalacuenta.utils.Utils

data class CuentaView(
    var id: Int? = null,
    var date: String? = null,
    var dateTime: String? = null,
    var numberItems: Int? = null,
    var title: MutableState<String?> = mutableStateOf(null),
    var total: MutableState<Double?> = mutableStateOf(null),
    var paymentMethod: MutableState<String?> = mutableStateOf(null)
) {
    constructor(response: CuentaEntity) : this(
        id = response.id,
        date = response.date,
        dateTime = Utils.formatDateTime(response.dateTime),
        numberItems = response.numberItems,
        title = mutableStateOf(response.title),
        total = mutableStateOf(response.total),
        paymentMethod = mutableStateOf(response.paymentMethod)
    )
}