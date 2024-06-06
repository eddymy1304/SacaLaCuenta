package com.example.sacalacuenta.domain

import com.example.sacalacuenta.data.CuentaRepository
import javax.inject.Inject

class SaveUserName @Inject constructor(
    private val repository: CuentaRepository
) {

    suspend operator fun invoke(userName: String) {
        repository.saveUserName(userName)
    }

}