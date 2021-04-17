package com.dicoding.bfaa.consumerapp.data.mapper

interface ModelMapper<Model, Entity> {

    fun mapFromEntity(entity: Entity) : Model

    fun mapToEntity(model: Model) : Entity
}