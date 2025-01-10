package com.eddymy1304.sacalacuenta.core.domain.mappers

interface EntityToDomainMapper<Entity, Domain> {

    fun asDomain(entity: Entity): Domain

    fun asEntity(domain: Domain): Entity
}