package com.dicoding.bfaa.consumerapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "following",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["username"],
        childColumns = ["owner"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [androidx.room.Index("owner")]
)
data class FollowingEntity(
    @ColumnInfo(name = "owner") val owner: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "avatar") val avatar: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var followingEntityId: Long = 0
}