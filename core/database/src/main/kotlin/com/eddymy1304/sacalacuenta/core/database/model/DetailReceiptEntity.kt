package com.eddymy1304.sacalacuenta.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DetailReceiptEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "id_receipt") val idReceipt: Int,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "quantity") val quantity: Double? = null,
    @ColumnInfo(name = "price") val price: Double? = null,
    @ColumnInfo(name = "total") val total: Double? = null
)