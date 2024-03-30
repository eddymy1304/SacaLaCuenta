package com.example.sacalacuenta.domain

import com.example.sacalacuenta.data.CuentaRepository
import com.example.sacalacuenta.data.models.CuentaView
import com.example.sacalacuenta.data.models.CuentaWithDetalleView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCuentas @Inject constructor(
    private val repository: CuentaRepository
) {

    operator fun invoke() = repository.getCuentasWithDetalles()

    fun getLastCuentaWithDetalle() = flow {
        repository.getLastCuentaWithDetalles().collect {
            emit(CuentaWithDetalleView(it))
        }
    }
}