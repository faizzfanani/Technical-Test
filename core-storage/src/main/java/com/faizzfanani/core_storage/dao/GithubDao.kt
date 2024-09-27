package com.faizzfanani.core_storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faizzfanani.core_storage.entity.GithubEntity

@Dao
interface GithubDao {
    @Query("SELECT * FROM table_github")
    fun getNews(): List<GithubEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(data: List<GithubEntity>)

    @Query("DELETE FROM table_github WHERE id = :id")
    suspend fun deleteNews(id: String)
}