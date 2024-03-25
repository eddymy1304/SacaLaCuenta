package com.example.sacalacuenta.domain

import com.example.sacalacuenta.data.CuentaRepository
import com.example.sacalacuenta.data.db.CuentaEntity
import com.example.sacalacuenta.data.db.DetalleCuentaEntity
import com.example.sacalacuenta.data.models.CuentaView
import com.example.sacalacuenta.data.models.DetalleCuentaView
import com.example.sacalacuenta.utils.Utils
import javax.inject.Inject

class SaveCuenta @Inject constructor(
    private val repository: CuentaRepository
) {
    suspend operator fun invoke(cuenta: CuentaView, listDet: List<DetalleCuentaView>) {
        val cuentaEntity = CuentaEntity(
            title = cuenta.title,
            paymentMethod = cuenta.paymentMethod,
            numberItems = listDet.size,
            total = listDet.sumOf { it.total ?: 0.0 },
            date = Utils.getDate(),
            dateTime = Utils.getDateTime()
        )

        repository.saveCuentas(cuentaEntity)

        val newCuenta = repository.getLastCuentaRegister()

        val listDetalleCuenta = listDet.map {
            DetalleCuentaEntity(
                idCuenta = newCuenta.id,
                name = it.name,
                quantity = it.quantity,
                price = it.price,
                total = it.total
            )
        }

        repository.saveListDetalleCuenta(listDetalleCuenta)
    }
}