package com.example.checkpoint2.network

import com.example.checkpoint2.model.Emoji
import retrofit2.http.GET

interface EmojiApiService {
    /**
     * Returns Map<String, String> of [Emoji] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("emojis")
    suspend fun getEmojis() : Map<String, String>
}
