package com.eddymy1304.sacalacuenta.domain

import com.eddymy1304.sacalacuenta.data.ReceiptRepository
import javax.inject.Inject

class GetUserName @Inject constructor(
    private val repository: ReceiptRepository
) {
    operator fun invoke() = repository.getUserName()
}