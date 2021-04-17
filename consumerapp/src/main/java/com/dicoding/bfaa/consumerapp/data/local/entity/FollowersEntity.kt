package com.dicoding.bfaa.consumerapp.data.local.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "followers",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["username"],
        childColumns = ["owner"],
        onDelete = CASCADE
    )],
    indices = [Index("owner")]
)
data class FollowersEntity(
    @ColumnInfo(name = "owner") val owner: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "avatar") val avatar: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var followersEntityId: Long = 0
}