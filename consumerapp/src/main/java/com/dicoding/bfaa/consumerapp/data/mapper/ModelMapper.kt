package com.dicoding.bfaa.consumerapp.data.mapper

interface ModelMapper<Model, Cursor> {

    fun mapFromEntity(entity: Cursor) : Model

    fun mapToEntity(model: Model) : Cursor
}