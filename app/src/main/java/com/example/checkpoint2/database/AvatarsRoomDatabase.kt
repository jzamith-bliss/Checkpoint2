package com.example.checkpoint2.database


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.checkpoint2.network.AvatarDao
import com.example.checkpoint2.network.AvatarData



@Database(entities = [AvatarData::class], version = 1, exportSchema = false)
abstract class AvatarsRoomDatabase : RoomDatabase() {

    abstract val avatarDao: AvatarDao

    companion object {

        @Volatile
        private var INSTANCE: AvatarsRoomDatabase? = null

        fun getDatabase(context: Context): AvatarsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AvatarsRoomDatabase::class.java,
                    "avatar_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance

            }
        }

    }

}