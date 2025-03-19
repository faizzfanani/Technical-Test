package com.faizzfanani.core_storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_pcs")
data class PcsEntity(
    @PrimaryKey
    val id: String,
    val createdAt: String,
    val name: String,
    val avatar: String,
    val address: String,
)
