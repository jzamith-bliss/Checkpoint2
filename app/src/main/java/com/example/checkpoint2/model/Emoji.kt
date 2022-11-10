package com.example.checkpoint2.model

import com.squareup.moshi.Json

data class Emoji (
    //@DrawableRes var imageResourceId: Int
    val id: String,
    //@Json(name = "emoji_name") val emojiName: String? = null,
    @Json(name = "emoji_url") val emojiUrl: String // ? = null serve para se não receber nada dá null
    )
