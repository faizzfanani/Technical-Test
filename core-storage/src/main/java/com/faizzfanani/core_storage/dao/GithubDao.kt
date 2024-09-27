package com.faizzfanani.core_storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faizzfanani.core_storage.entity.GithubEntity

@Dao
interface GithubDao {
    @Query("SELECT * FROM user_github")
    fun getUsers(): List<GithubEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(data: List<GithubEntity>)

    @Query("DELETE FROM user_github WHERE id = :id")
    suspend fun deleteUser(id: Int)
}