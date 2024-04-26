package com.example.sacalacuenta.domain

import com.example.sacalacuenta.data.CuentaRepository
import com.example.sacalacuenta.data.models.CuentaWithDetalleView
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCuentas @Inject constructor(
    private val repository: CuentaRepository
) {

    operator fun invoke() = flow {
        repository.getCuentasWithDetalles().collect { cuenta ->
            val listMap =
                cuenta.map { CuentaWithDetalleView(it) }.sortedByDescending { it.cuenta.id }
            emit(listMap)
        }
    }

    fun getLastCuentaWithDetalle() = flow {
        repository.getLastCuentaWithDetalles().collect {
            emit(CuentaWithDetalleView(it))
        }
    }

    fun getCuentasByDate(date: String) = flow {
        repository.getCuentasByDate(date).collect { cuenta ->
            val listMap = cuenta.map {
                CuentaWithDetalleView(it)
            }.sortedByDescending { it.cuenta.id }
            emit(listMap)
        }
    }


}