package com.eddymy1304.sacalacuenta.core.domain

import com.eddymy1304.sacalacuenta.core.common.Utils
import com.eddymy1304.sacalacuenta.core.data.repository.ReceiptRepository
import com.eddymy1304.sacalacuenta.core.domain.mappers.asEntity
import com.eddymy1304.sacalacuenta.core.model.DetailReceipt
import com.eddymy1304.sacalacuenta.core.model.Receipt
import javax.inject.Inject

class SaveReceipt @Inject constructor(
    private val repository: ReceiptRepository
) {
    suspend operator fun invoke(receipt: Receipt, listDet: List<DetailReceipt>) {


        val receiptEntity = receipt.apply {
            numberItems = listDet.size
            total = listDet.sumOf { it.total }
            date = Utils.getDate()
            dateTime = Utils.getDateTime()
        }.asEntity()

        repository.saveReceipts(receiptEntity)

        val newReceipt = repository.getLastReceiptRegister()

        val listDetailReceipt = listDet.map {
            it.idReceipt = newReceipt.id
            it.asEntity()
        }

        repository.saveListDetailReceipt(listDetailReceipt)
    }
}