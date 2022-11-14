package com.example.checkpoint2.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.checkpoint2.database.EmojiRoomDatabase
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmojiManager(private val database: EmojiRoomDatabase) {

    //private var _emojis = MutableLiveData<List<Emoji>>()
    //val emojis: LiveData<List<Emoji>>
    //    get() = _emojis

    //val emojis: Flow<List<Emoji>> = database.emojiDao.getEmojis().mapLatest { it.asEmoji() }
    val emojis: LiveData<List<Emoji>> =
        Transformations.map(database.emojiDao.getEmojis()) { it.asEmoji() }

    suspend fun refreshEmojis() {
        withContext(Dispatchers.IO) {
            database.emojiDao.clearEmojis()
            val emojiList = EmojiApi.retrofitService.getEmojis()
            database.emojiDao.insertAll(emojiList.asEmojiData())
            //_emojis = Transformations.map(database.emojiDao.getEmojis()) { it.asEmoji() } as MutableLiveData<List<Emoji>>
            //Log.v("Testing", "Ending refresh")
            //Log.v("Testing", database.emojiDao.getEmojis().value!!.size.toString())
        }
    }

}

object EmojisNetwork {
    suspend fun getEmojisNetwork(): List<Emoji> {
        return EmojiApi.retrofitService.getEmojis().asEmojiData().asEmoji()//.toMutableList()
    }
}

