package com.example.checkpoint2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.checkpoint2.network.RepoData
import com.example.checkpoint2.network.ReposDao

@Database(entities = [RepoData::class], version = 1, exportSchema = false)
abstract class ReposRoomDatabase : RoomDatabase() {

    abstract val reposDao: ReposDao

    companion object {

        @Volatile
        private var INSTANCE: ReposRoomDatabase? = null

        fun getDatabase(context: Context): ReposRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReposRoomDatabase::class.java,
                    "repos_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance

            }
        }

    }

}