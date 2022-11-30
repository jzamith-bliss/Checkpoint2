package com.example.checkpoint2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CheckpointApplication: Application() {
    //val database: EmojiRoomDatabase by lazy { EmojiRoomDatabase.getDatabase(this) }
}