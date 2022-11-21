package com.example.checkpoint2.network

import com.example.checkpoint2.model.Emoji
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://api.github.com"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getEmojis] method
 */

interface EmojiApiService {
    /**
     * Returns Map<String, String> of [Emoji] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("emojis")
    suspend fun getEmojis() : Map<String, String>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object EmojiApi {
    val retrofitService: EmojiApiService by lazy { retrofit.create(EmojiApiService::class.java) }
}

fun Map<String, String>.asEmojiData(): List<EmojiData> {
    return map {
        EmojiData(
            emojiName = it.key,
            emojiUrl = it.value
        )
    }
}