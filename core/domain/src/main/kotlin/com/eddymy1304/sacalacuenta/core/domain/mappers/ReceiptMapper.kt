package com.eddymy1304.sacalacuenta.core.domain.mappers

import com.eddymy1304.sacalacuenta.core.database.model.ReceiptEntity
import com.eddymy1304.sacalacuenta.core.model.Receipt

object ReceiptMapper : EntityToDomainMapper<ReceiptEntity, Receipt> {
    override fun asDomain(entity: ReceiptEntity): Receipt {
        return Receipt(
            id = entity.id,
            date = entity.date ?: "",
            dateTime = entity.dateTime ?: "",
            numberItems = entity.numberItems ?: 0,
            title = entity.title ?: "",
            total = entity.total ?: 0.00,
            paymentMethod = entity.paymentMethod ?: ""
        )
    }

    override fun asEntity(domain: Receipt): ReceiptEntity {
        return ReceiptEntity(
            id = -1,
            title = domain.title,
            total = domain.total,
            numberItems = domain.numberItems,
            paymentMethod = domain.paymentMethod,
            date = domain.date,
            dateTime = domain.dateTime
        )
    }
}

fun ReceiptEntity.asDomain() = ReceiptMapper.asDomain(this)

fun Receipt.asEntity() = ReceiptMapper.asEntity(this)

fun List<ReceiptEntity>.asDomain() = this.map { it.asDomain() }

fun List<Receipt>.asEntity() = this.map { it.asEntity() }