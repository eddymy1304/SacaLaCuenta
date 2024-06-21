package com.eddymy1304.sacalacuenta.domain

import com.eddymy1304.sacalacuenta.data.CuentaRepository
import javax.inject.Inject

class GetUserName @Inject constructor(
    private val repository: CuentaRepository
) {
    operator fun invoke() = repository.getUserName()
}