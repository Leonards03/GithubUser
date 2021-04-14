package com.dicoding.bfaa.githubuser.data.mapper

import com.dicoding.bfaa.githubuser.data.local.entity.RepositoryEntity
import com.dicoding.bfaa.githubuser.data.model.Repository
import com.dicoding.bfaa.githubuser.data.network.responses.RepositoryResponse

class RepositoryMapper : ModelMapper<RepositoryResponse, Repository, RepositoryEntity> {
    override fun mapFromResponse(response: RepositoryResponse): Repository =
        Repository(
            owner = response.owner.username,
            name = response.name,
            url = response.url,
            language = response.language ?: String()
        )

    fun mapFromResponses(responses: List<RepositoryResponse>): List<Repository> =
        responses.map { response -> mapFromResponse(response) }

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


    fun mapFromEntites(entities: List<RepositoryEntity>): List<Repository> =
        entities.map { entity -> mapFromEntity(entity) }

}