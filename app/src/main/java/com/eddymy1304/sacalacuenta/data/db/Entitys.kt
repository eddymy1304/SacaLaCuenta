package com.eddymy1304.sacalacuenta.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

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

@Entity
data class DetailReceiptEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "id_receipt") val idReceipt: Int,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "quantity") val quantity: Double? = null,
    @ColumnInfo(name = "price") val price: Double? = null,
    @ColumnInfo(name = "total") val total: Double? = null
)

data class ReceiptWithDetailReceipt(
    @Embedded val receipt: ReceiptEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_receipt"
    )
    val listDetail: List<DetailReceiptEntity>
)