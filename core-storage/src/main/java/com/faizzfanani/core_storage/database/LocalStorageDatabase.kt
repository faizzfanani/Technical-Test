package com.faizzfanani.core_storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faizzfanani.core_storage.dao.GithubDao
import com.faizzfanani.core_storage.entity.GithubEntity

@Database(
    entities = [GithubEntity::class],
    version = 1,

    /*AUTO MIGRATION
    autoMigrations = [

        /*ADD / DELETE TABLE OR COLUMN
        AutoMigration(from = 1, to = 2),
        */

        /*RENAME TABLE OR COLUMN
        AutoMigration(from = 2, to = 3, spec = LocalStorageDatabase.MyAutoMigration::class),
        endregion*/
    ],
    */

    exportSchema = true
)
abstract class LocalStorageDatabase : RoomDatabase(){
    abstract fun githubDao(): GithubDao

    /*RENAME COLUMN EXAMPLE
    @RenameColumn(tableName = "user_github", fromColumnName = "tesColumnTes", toColumnName = "singleColumn")
    class MyAutoMigration : AutoMigrationSpec
    */
}