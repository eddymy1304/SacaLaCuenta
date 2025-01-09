package com.eddymy1304.sacalacuenta.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReceiptEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "total") val total: Double? = null,
    @ColumnInfo(name = "number_items") val numberItems: Int? = null,
    @ColumnInfo(name = "payment_method") val paymentMethod: String? = null,
    @ColumnInfo(name = "date") val date: String? = null,
    @ColumnInfo(name = "date_time") val dateTime: String? = null
)