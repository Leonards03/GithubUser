package com.dicoding.bfaa.consumerapp.data.mapper

import android.database.Cursor
import androidx.core.database.getStringOrNull
import com.dicoding.bfaa.consumerapp.data.model.Repository

class RepositoryMapper : ModelMapper<Repository, Cursor> {
    override fun mapFromCursor(cursor: Cursor): Repository {
        cursor.apply {
            val owner = getString(getColumnIndex("owner"))
            val name = getString(getColumnIndex("name"))
            val url = getString(getColumnIndex("url"))
            val language = valueOrEmpty(getStringOrNull(getColumnIndex("language")))
            return Repository(owner, name, url, language)
        }
    }


    private fun valueOrEmpty(value: String?): String {
        return value ?: String()
    }
}