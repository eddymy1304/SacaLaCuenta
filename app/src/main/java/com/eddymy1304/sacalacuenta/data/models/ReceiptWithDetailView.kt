package com.eddymy1304.sacalacuenta.data.models

import com.eddymy1304.sacalacuenta.data.db.ReceiptWithDetailReceipt

data class ReceiptWithDetailView(
    val receipt: ReceiptView = ReceiptView(),
    val listDetailReceipt: List<DetailReceiptView> = listOf()
) {
    constructor(response : ReceiptWithDetailReceipt) : this(
        receipt = ReceiptView(response.receipt),
        listDetailReceipt = response.listDetail.map { DetailReceiptView(it) }
    )
}
