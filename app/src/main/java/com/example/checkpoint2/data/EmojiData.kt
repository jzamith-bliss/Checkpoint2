package com.example.checkpoint2.data

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

fun List<EmojiData>.asEmoji(): List<Emoji> { return map { (it.asEmoji()) } }
fun EmojiData.asEmoji(): Emoji { return  Emoji(this.emojiName, emojiUrl = this.emojiUrl) }

fun Map<String, String>.asEmojiData(): List<EmojiData> {
    return map {
        EmojiData(
            emojiName = it.key,
            emojiUrl = it.value
        )
    }
}