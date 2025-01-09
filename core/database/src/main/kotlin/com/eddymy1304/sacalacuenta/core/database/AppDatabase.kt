package com.eddymy1304.sacalacuenta.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eddymy1304.sacalacuenta.core.database.dao.ReceiptDao
import com.eddymy1304.sacalacuenta.core.database.model.DetailReceiptEntity
import com.eddymy1304.sacalacuenta.core.database.model.ReceiptEntity

@Database(entities = [ReceiptEntity::class, DetailReceiptEntity::class], version = 4)
internal abstract class AppDataBase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDao
}