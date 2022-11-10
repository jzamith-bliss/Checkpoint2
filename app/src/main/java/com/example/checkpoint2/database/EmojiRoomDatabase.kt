package com.example.checkpoint2.database


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.checkpoint2.network.EmojiDao
import com.example.checkpoint2.network.EmojiData


@Database(entities = [EmojiData::class], version = 1, exportSchema = false)
abstract class EmojiRoomDatabase : RoomDatabase() {

    abstract val emojiDao: EmojiDao

    companion object {

        @Volatile
        private var INSTANCE: EmojiRoomDatabase? = null

        fun getDatabase(context: Context): EmojiRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmojiRoomDatabase::class.java,
                    "emoji_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance

            }
        }

    }

}