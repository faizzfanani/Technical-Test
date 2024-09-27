package com.faizzfanani.core_storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_github")
data class GithubEntity(
    @PrimaryKey
    val id: Int,
    val username: String,
    val avatarUrl: String,
    val profileUrl: String,
    val followersCount: Int,
    val followingCount: Int,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: String,
    val bio: String,
    val publicReposCount: Int,
    val publicGistsCount: Int,
    val createdAt: String,
    val updatedAt: String
)
