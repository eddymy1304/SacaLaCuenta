package com.example.sacalacuenta.data

import com.example.sacalacuenta.data.db.CuentaEntity
import com.example.sacalacuenta.data.db.Daos
import com.example.sacalacuenta.data.db.DetalleCuentaEntity
import com.example.sacalacuenta.data.prefs.CuentaPreferences
import javax.inject.Inject

class CuentaRepository @Inject constructor(
    private val cuentaDao: Daos,
    private val preferences: CuentaPreferences
) {
    suspend fun saveCuentas(vararg cuentas: CuentaEntity) = cuentaDao.insertCuentas(*cuentas)

    suspend fun getLastCuentaRegister() = cuentaDao.getLastCuentaRegister()

    suspend fun saveListDetalleCuenta(list: List<DetalleCuentaEntity>) =
        cuentaDao.insertDetalleCuentas(list)

    fun getCuentasWithDetalles() = cuentaDao.getCuentasWithDetalles()

    fun getLastCuentaWithDetalles() = cuentaDao.getLastCuentaWithDetalles()

    fun getCuentasByDate(date: String) = cuentaDao.getCuentasByDate(date)

    fun getUserName() = preferences.getUserName()

    suspend fun saveUserName(userName: String) = preferences.saveUserName(userName)
}