package com.dicoding.bfaa.githubuser.data.mapper

interface ModelMapper<Response, Model, Entity> {
    fun mapFromResponse(response: Response) : Model

    fun mapFromEntity(entity: Entity) : Model

    fun mapToEntity(model: Model) : Entity
}