package com.eddymy1304.sacalacuenta.core.model

data class ReceiptWithDetail(
    val receipt: Receipt = Receipt(),
    val listDetailReceipt: List<DetailReceipt> = listOf()
)
