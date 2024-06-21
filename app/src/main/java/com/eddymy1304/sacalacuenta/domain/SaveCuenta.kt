package com.eddymy1304.sacalacuenta.domain

import com.eddymy1304.sacalacuenta.data.CuentaRepository
import com.eddymy1304.sacalacuenta.data.db.CuentaEntity
import com.eddymy1304.sacalacuenta.data.db.DetalleCuentaEntity
import com.eddymy1304.sacalacuenta.data.models.CuentaView
import com.eddymy1304.sacalacuenta.data.models.DetalleCuentaView
import com.eddymy1304.sacalacuenta.utils.Utils
import javax.inject.Inject

class SaveCuenta @Inject constructor(
    private val repository: CuentaRepository
) {
    suspend operator fun invoke(cuenta: CuentaView, listDet: List<DetalleCuentaView>) {

        val cuentaEntity = CuentaEntity(
            title = cuenta.title.value.orEmpty(),
            paymentMethod = cuenta.paymentMethod.value.orEmpty(),
            numberItems = listDet.size,
            total = listDet.sumOf { it.total.value ?: 0.0 },
            date = Utils.getDate(),
            dateTime = Utils.getDateTime()
        )

        repository.saveCuentas(cuentaEntity)

        val newCuenta = repository.getLastCuentaRegister()

        val listDetalleCuenta = listDet.map {
            DetalleCuentaEntity(
                idCuenta = newCuenta.id,
                name = it.name.value,
                quantity = it.quantity.value,
                price = it.price.value,
                total = it.total.value
            )
        }

        repository.saveListDetalleCuenta(listDetalleCuenta)
    }
}