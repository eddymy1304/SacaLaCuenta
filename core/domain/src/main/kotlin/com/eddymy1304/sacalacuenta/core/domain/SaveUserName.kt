package com.eddymy1304.sacalacuenta.core.domain

import com.eddymy1304.sacalacuenta.core.data.repository.ReceiptRepository
import javax.inject.Inject

class SaveUserName @Inject constructor(
    private val repository: ReceiptRepository
) {

    suspend operator fun invoke(userName: String) {
        repository.saveUserName(userName)
    }

}