package com.eddymy1304.sacalacuenta.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class ReceiptWithDetailReceipt(
    @Embedded val receipt: ReceiptEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_receipt"
    )
    val listDetail: List<DetailReceiptEntity>
)