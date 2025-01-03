package com.eddymy1304.sacalacuenta.domain

import com.eddymy1304.sacalacuenta.data.ReceiptRepository
import com.eddymy1304.sacalacuenta.data.models.ReceiptWithDetailView
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetReceipts @Inject constructor(
    private val repository: ReceiptRepository
) {

    operator fun invoke() = flow {
        repository.getReceiptsWithDetail().collect { receipt ->
            val listMap = receipt
                .map { ReceiptWithDetailView(it) }
                .sortedByDescending { it.receipt.id }
            emit(listMap)
        }
    }

    fun getLastReceiptWithDetail() = flow {
        repository.getLastReceiptWithDetail().collect {
            emit(ReceiptWithDetailView(it))
        }
    }

    fun getReceiptsByDate(date: String) = flow {
        repository.getReceiptsByDate(date).collect { receipt ->
            val listMap = receipt.map {
                ReceiptWithDetailView(it)
            }.sortedByDescending { it.receipt.id }
            emit(listMap)
        }
    }

    fun getReceiptWithDetById(id: Int) = flow {
        repository.getReceiptWithDetById(id).collect {
            emit(ReceiptWithDetailView(it))
        }
    }

}