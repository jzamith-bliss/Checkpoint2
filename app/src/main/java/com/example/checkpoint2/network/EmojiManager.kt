package com.example.checkpoint2.network

import com.example.checkpoint2.model.Emoji

object EmojisNetwork {
    suspend fun getEmojisNetwork(): List<Emoji> {
        return EmojiApi.retrofitService.getEmojis().asEmojiData().asEmoji()//.toMutableList()
    }
}

//TODO: GetEmojiList criar
object EmojisDatabase {
    //private val emojiRepository = EmojiRepository(EmojiRoomDatabase.getDatabase())
    //val databaseEmojis: LiveData<List<Emoji>> = emojiRepository.emojis
}