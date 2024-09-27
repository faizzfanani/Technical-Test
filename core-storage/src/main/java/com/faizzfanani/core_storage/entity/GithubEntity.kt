package com.faizzfanani.core_storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_github")
data class GithubEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)
