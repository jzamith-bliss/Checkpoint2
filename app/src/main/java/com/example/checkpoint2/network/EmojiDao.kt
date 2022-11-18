package com.example.checkpoint2.network

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.checkpoint2.model.Emoji

@Dao
interface EmojiDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(emojis: List<EmojiData>)

    @Delete
    suspend fun delete(emoji: EmojiData)

    @Query("SELECT * FROM emoji")
    fun getEmojis(): LiveData<List<EmojiData>>

    @Query("SELECT * FROM emoji")
    fun getEmojisFromDatabase(): List<EmojiData>

    @Query("SELECT * FROM emoji WHERE id = :id")
    fun getRandomEmoji(id: Int): LiveData<EmojiData>

    @Query("DELETE FROM emoji")
    fun clearEmojis()

    @Query("SELECT EXISTS (SELECT * FROM emoji)")
    fun exists(): Boolean

    @Query("SELECT * FROM emoji WHERE emoji_url = :url LIMIT 1 ")
    fun getEmojiByUrl(url: String): EmojiData
}