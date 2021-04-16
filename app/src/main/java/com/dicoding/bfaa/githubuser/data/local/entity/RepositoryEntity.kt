package com.dicoding.bfaa.githubuser.data.local.entity

import androidx.room.*

@Entity(
    tableName = "repositories",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["username"],
        childColumns = ["owner"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("owner")]
)
data class RepositoryEntity(
    @ColumnInfo val owner: String,
    @ColumnInfo val name: String,
    @ColumnInfo val url: String,
    @ColumnInfo val language: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var repositoryId: Long = 0
}