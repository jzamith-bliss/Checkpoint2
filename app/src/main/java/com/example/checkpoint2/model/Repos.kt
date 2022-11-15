package com.example.checkpoint2.model

import com.squareup.moshi.Json

data class Repos (
    //val id: String,
    //@Json(name = "avatar_name") val avatarName: String,
    @Json(name = "full_name") val repoName: String // ? = null serve para se não receber nada dá null
)