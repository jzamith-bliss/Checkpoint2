package com.example.checkpoint2.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.checkpoint2.data.EmojiData
import com.example.checkpoint2.data.asEmoji
import com.example.checkpoint2.data.asEmojiData
import com.example.checkpoint2.database.EmojiDao
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmojiManager @Inject constructor(
    private val emojiDao: EmojiDao,
    private val emojiApiService: EmojiApiService
    ) {

    val emojis: LiveData<List<Emoji>> =
        Transformations.map(emojiDao.getEmojis()) { it.asEmoji() }

    suspend fun getEmojis(): List<Emoji> {
        return if (checkEmojisInDisk()) {getEmojisFromDisk()}
        else { getEmojisFromNetwork().also { insertEmojisInDiskFromNetwork()}}
    }

    private suspend fun checkEmojisInDisk(): Boolean {
        return withContext(Dispatchers.IO) {
            emojiDao.exists()
        }
    }

    private suspend fun getEmojisFromDisk(): List<Emoji>{
        return withContext(Dispatchers.IO) {
            emojiDao.getEmojisFromDatabase().asEmoji()
        }
    }

    private suspend fun getEmojisFromNetwork(): List<Emoji> {
        return emojiApiService.getEmojis().asEmojiData().asEmoji()
    }

    private suspend fun getEmojisDataFromNetwork(): List<EmojiData> {
        return emojiApiService.getEmojis().asEmojiData()
    }

    private suspend fun insertEmojisInDiskFromNetwork() {
       return withContext(Dispatchers.IO) {
            emojiDao.insertAll(getEmojisDataFromNetwork())
        }
    }

    suspend fun getEmojiByUrlFromDatabase(url: String): Emoji {
        return withContext(Dispatchers.IO) {
            emojiDao.getEmojiByUrl(url).asEmoji()
        }
    }
}


