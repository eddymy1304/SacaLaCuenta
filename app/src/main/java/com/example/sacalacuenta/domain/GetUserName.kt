package com.example.sacalacuenta.domain

import com.example.sacalacuenta.data.CuentaRepository
import javax.inject.Inject

class GetUserName @Inject constructor(
    private val repository: CuentaRepository
) {
    operator fun invoke() = repository.getUserName()
}