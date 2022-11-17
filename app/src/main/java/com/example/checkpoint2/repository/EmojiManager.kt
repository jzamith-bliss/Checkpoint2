package com.example.checkpoint2.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.checkpoint2.database.EmojiRoomDatabase
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmojiManager(private val database: EmojiRoomDatabase) {

    val emojis: LiveData<List<Emoji>> =
        Transformations.map(database.emojiDao.getEmojis()) { it.asEmoji() }

    suspend fun getEmojis(): List<Emoji> {
        return if (checkEmojisInDisk()) {getEmojisFromDisk()}
        else { getEmojisFromNetwork().also { insertEmojisInDiskFromNetwork()}}
    }

    suspend fun checkEmojisInDisk(): Boolean {
        return withContext(Dispatchers.IO) {
            database.emojiDao.exists()
        }
    }

    suspend fun getEmojisFromDisk(): List<Emoji>{
        return withContext(Dispatchers.IO) {
            database.emojiDao.getEmojisFromDatabase().asEmoji()
        }
    }

    suspend fun getEmojisFromNetwork(): List<Emoji> {
        return EmojiApi.retrofitService.getEmojis().asEmojiData().asEmoji()
    }

    private suspend fun getEmojisDataFromNetwork(): List<EmojiData> {
        return EmojiApi.retrofitService.getEmojis().asEmojiData()
    }

    suspend fun insertEmojisInDiskFromNetwork() {
       return withContext(Dispatchers.IO) {
            database.emojiDao.insertAll(getEmojisDataFromNetwork())
        }
    }


}


