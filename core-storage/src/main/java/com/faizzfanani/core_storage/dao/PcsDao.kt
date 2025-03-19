package com.faizzfanani.core_storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faizzfanani.core_storage.entity.PcsEntity

@Dao
interface PcsDao {
    @Query("SELECT * FROM user_pcs")
    fun getUsers(): List<PcsEntity>

    @Query("SELECT * FROM user_pcs WHERE id = :id")
    fun getUserById(id: String): PcsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(data: List<PcsEntity>)
}