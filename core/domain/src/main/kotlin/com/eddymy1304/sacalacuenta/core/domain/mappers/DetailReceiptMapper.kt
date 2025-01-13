package com.eddymy1304.sacalacuenta.core.domain.mappers

import com.eddymy1304.sacalacuenta.core.database.model.DetailReceiptEntity
import com.eddymy1304.sacalacuenta.core.model.DetailReceipt

object DetailReceiptMapper : EntityToDomainMapper<DetailReceiptEntity, DetailReceipt> {
    override fun asDomain(entity: DetailReceiptEntity): DetailReceipt {
        return DetailReceipt(
            id = entity.id,
            idReceipt = entity.idReceipt,
            name = entity.name ?: "",
            amount = entity.quantity ?: 0.00,
            price = entity.price ?: 0.00,
            total = entity.total ?: 0.00
        )
    }

    override fun asEntity(domain: DetailReceipt): DetailReceiptEntity {
        return DetailReceiptEntity(
            id = domain.id,
            idReceipt = domain.idReceipt,
            name = domain.name,
            quantity = domain.amount,
            price = domain.price,
            total = domain.total
        )
    }
}

fun DetailReceiptEntity.asDomain() = DetailReceiptMapper.asDomain(this)

fun DetailReceipt.asEntity() = DetailReceiptMapper.asEntity(this)

fun List<DetailReceiptEntity>.asDomain() = this.map { it.asDomain() }

fun List<DetailReceipt>.asEntity() = this.map { it.asEntity() }