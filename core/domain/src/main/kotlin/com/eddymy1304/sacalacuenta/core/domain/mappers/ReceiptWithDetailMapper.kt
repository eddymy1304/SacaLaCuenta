package com.eddymy1304.sacalacuenta.core.domain.mappers

import com.eddymy1304.sacalacuenta.core.database.model.ReceiptWithDetailReceipt
import com.eddymy1304.sacalacuenta.core.model.ReceiptWithDetail

fun ReceiptWithDetailReceipt.asDomain() = ReceiptWithDetail(
    receipt = this.receipt.asDomain(),
    listDetailReceipt = this.listDetail.asDomain()
)

fun List<ReceiptWithDetailReceipt>.asDomain() = this.map { it.asDomain() }