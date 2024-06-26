package com.eddymy1304.sacalacuenta.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CuentaEntity::class, DetalleCuentaEntity::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun cuentaDao(): Daos
}