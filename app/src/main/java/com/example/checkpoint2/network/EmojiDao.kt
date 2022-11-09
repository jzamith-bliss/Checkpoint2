package com.example.checkpoint2.network

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.checkpoint2.network.EmojiData
import kotlinx.coroutines.flow.Flow

@Dao
interface EmojiDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(emojis: List<EmojiData>)

    @Delete
    suspend fun delete(emoji: EmojiData)

    @Query("SELECT * FROM emoji")
    fun getEmojis(): LiveData<List<EmojiData>>

    @Query("SELECT * FROM emoji WHERE id = :id")
    fun getRandomEmoji(id: Int): LiveData<EmojiData>

    @Query("DELETE FROM emoji")
    fun clearEmojis()

}