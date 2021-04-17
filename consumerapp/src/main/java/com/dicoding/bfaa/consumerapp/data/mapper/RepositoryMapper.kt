package com.dicoding.bfaa.consumerapp.data.mapper

import com.dicoding.bfaa.consumerapp.data.local.entity.RepositoryEntity
import com.dicoding.bfaa.consumerapp.data.model.Repository

class RepositoryMapper : ModelMapper<Repository, RepositoryEntity> {
    override fun mapFromEntity(entity: RepositoryEntity): Repository =
        Repository(
            owner = entity.owner,
            name = entity.name,
            url = entity.url,
            language = entity.language
        )

    override fun mapToEntity(model: Repository): RepositoryEntity =
        RepositoryEntity(
            owner = model.owner,
            name = model.name,
            url = model.url,
            language = model.language
        )

    fun mapToEntities(repositories: List<Repository>): List<RepositoryEntity> =
        repositories.map { repository -> mapToEntity(repository) }


    fun mapFromEntities(entities: List<RepositoryEntity>): List<Repository> =
        entities.map { entity -> mapFromEntity(entity) }

}