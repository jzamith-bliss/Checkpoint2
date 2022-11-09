package com.example.checkpoint2

import android.app.Application
import com.example.checkpoint2.database.EmojiRoomDatabase

class CheckpointApplication: Application() {
    val database: EmojiRoomDatabase by lazy { EmojiRoomDatabase.getDatabase(this) }
}