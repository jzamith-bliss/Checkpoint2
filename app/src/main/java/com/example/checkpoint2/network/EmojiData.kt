package com.example.checkpoint2.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.checkpoint2.model.Emoji

@Entity(tableName = "emoji")
data class EmojiData(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "emoji_name")
    val emojiName: String,

    @ColumnInfo(name = "emoji_url")
    val emojiUrl: String

)

fun List<EmojiData>.asEmoji(): List<Emoji> { return map { Emoji(emojiUrl = it.emojiUrl) } }