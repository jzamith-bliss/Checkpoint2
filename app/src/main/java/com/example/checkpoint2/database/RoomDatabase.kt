package com.example.checkpoint2.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.checkpoint2.data.AvatarData
import com.example.checkpoint2.data.EmojiData
import com.example.checkpoint2.data.RepoData

@Database(entities = [EmojiData::class, AvatarData::class, RepoData::class], version = 1, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun emojiDao(): EmojiDao
    abstract fun avatarDao(): AvatarDao
    abstract fun reposDao(): ReposDao
}