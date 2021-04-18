package com.dicoding.bfaa.consumerapp.data.mapper

import android.database.Cursor
import com.dicoding.bfaa.consumerapp.data.model.User

@Suppress("UNCHECKED_CAST")
class UserMapper : ModelMapper<User, Cursor> {

    private fun valueOrEmpty(value: String?): String {
        return value ?: String()
    }

    override fun mapFromEntity(entity: Cursor): User {
        entity.apply {
            return User(
                getString(getColumnIndex("username")),
                getString(getColumnIndex("name")),
                getString(getColumnIndex("bio")),
                getString(getColumnIndex("avatar")),
                getString(getColumnIndex("location")),
                getString(getColumnIndex("company")),
                getInt(getColumnIndex("followers_count")),
                getInt(getColumnIndex("following_count"))
            )
        }
    }

    fun mapFromFollowEntity(entity: Cursor): User {
        entity.apply {
            return User(
                getString(getColumnIndex("username")),
                getString(getColumnIndex("avatar"))
            )
        }
    }

    override fun mapToEntity(model: User): Cursor {
        TODO("Not yet implemented")
    }
}