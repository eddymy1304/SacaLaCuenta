package com.eddymy1304.sacalacuenta.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ReceiptEntity::class, DetailReceiptEntity::class], version = 3)
abstract class AppDataBase : RoomDatabase() {
    abstract fun receiptDao(): Dao
}