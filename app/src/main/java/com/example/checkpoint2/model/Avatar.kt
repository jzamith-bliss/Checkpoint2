package com.example.checkpoint2.model

import com.squareup.moshi.Json

data class Avatar (
    val id: String,
    @Json(name = "avatar_url") val avatarUrl: String // ? = null serve para se não receber nada dá null
)