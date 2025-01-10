package com.eddymy1304.sacalacuenta.core.domain

import com.eddymy1304.sacalacuenta.core.data.repository.ReceiptRepository
import com.eddymy1304.sacalacuenta.core.domain.mappers.asDomain
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetReceipts @Inject constructor(
    private val repository: ReceiptRepository
) {

    operator fun invoke() = flow {
        repository.getReceiptsWithDetail().collect { receipt ->
            val listMap = receipt
                .asDomain()
                .sortedByDescending { it.receipt.id }
            emit(listMap)
        }
    }

    fun getLastReceiptWithDetail() = flow {
        repository.getLastReceiptWithDetail().collect {
            emit(it.asDomain())
        }
    }

    fun getReceiptsByDate(date: String) = flow {
        repository.getReceiptsByDate(date).collect { receipt ->
            val listMap = receipt
                .asDomain()
                .sortedByDescending { it.receipt.id }
            emit(listMap)
        }
    }

    fun getReceiptWithDetById(id: Int) = flow {
        repository.getReceiptWithDetById(id).collect {
            emit(it.asDomain())
        }
    }

}