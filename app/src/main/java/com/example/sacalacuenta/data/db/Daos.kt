package com.example.sacalacuenta.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Daos {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCuentas(vararg cuentas: CuentaEntity)

    @Query("SELECT * FROM CuentaEntity ORDER BY id DESC LIMIT 1")
    suspend fun getLastCuentaRegister(): CuentaEntity

    @Update
    suspend fun updateCuentas(vararg cuentas: CuentaEntity)

    @Delete
    suspend fun deleteCuentas(vararg cuentas: CuentaEntity)

    @Transaction
    @Query("SELECT * FROM CuentaEntity")
    fun getCuentasWithDetalles(): Flow<List<CuentaWithDetalleCuenta>>

    @Transaction
    @Query("SELECT * FROM CuentaEntity ORDER BY id DESC LIMIT 1")
    fun getLastCuentaWithDetalles(): Flow<CuentaWithDetalleCuenta>

    @Insert
    suspend fun insertDetalleCuentas(listDetalleCuenta: List<DetalleCuentaEntity>)

    @Update
    suspend fun updateDetalleCuentas(vararg detalleCuentas: DetalleCuentaEntity)

    @Delete
    suspend fun deleteDetalleCuentas(vararg detalleCuentas: DetalleCuentaEntity)
}