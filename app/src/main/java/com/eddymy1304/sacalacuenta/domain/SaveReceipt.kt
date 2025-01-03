package com.eddymy1304.sacalacuenta.domain

import com.eddymy1304.sacalacuenta.data.ReceiptRepository
import com.eddymy1304.sacalacuenta.data.db.ReceiptEntity
import com.eddymy1304.sacalacuenta.data.db.DetailReceiptEntity
import com.eddymy1304.sacalacuenta.data.models.ReceiptView
import com.eddymy1304.sacalacuenta.data.models.DetailReceiptView
import com.eddymy1304.sacalacuenta.utils.Utils
import javax.inject.Inject

class SaveReceipt @Inject constructor(
    private val repository: ReceiptRepository
) {
    suspend operator fun invoke(receipt: ReceiptView, listDet: List<DetailReceiptView>) {

        val receiptEntity = ReceiptEntity(
            title = receipt.title.value.orEmpty(),
            paymentMethod = receipt.paymentMethod.value.orEmpty(),
            numberItems = listDet.size,
            total = listDet.sumOf { it.total.value ?: 0.0 },
            date = Utils.getDate(),
            dateTime = Utils.getDateTime()
        )

        repository.saveReceipts(receiptEntity)

        val newReceipt = repository.getLastReceiptRegister()

        val listDetailReceipt = listDet.map {
            DetailReceiptEntity(
                idReceipt = newReceipt.id,
                name = it.name.value,
                quantity = it.quantity.value,
                price = it.price.value,
                total = it.total.value
            )
        }

        repository.saveListDetailReceipt(listDetailReceipt)
    }
}