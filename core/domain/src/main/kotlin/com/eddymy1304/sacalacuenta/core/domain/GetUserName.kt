package com.eddymy1304.sacalacuenta.core.domain

import com.eddymy1304.sacalacuenta.core.data.repository.ReceiptRepository
import javax.inject.Inject

class GetUserName @Inject constructor(
    private val repository: ReceiptRepository
) {
    operator fun invoke() = repository.getUserName()
}