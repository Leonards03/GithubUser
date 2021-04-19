package com.dicoding.bfaa.consumerapp.data.mapper

interface ModelMapper<Model, Cursor> {

    fun mapFromCursor(cursor: Cursor) : Model

}