package com.example.sacalacuenta.domain

import com.example.sacalacuenta.data.CuentaRepository
import javax.inject.Inject

class GetCuentas @Inject constructor(
    private val repository: CuentaRepository
) {

    operator fun invoke() = repository.getCuentasWithDetalles()

}