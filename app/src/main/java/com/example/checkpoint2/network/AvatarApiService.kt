package com.example.checkpoint2.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL =
    "https://api.github.com/"

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
 * A public interface that exposes the [getAvatar] method
 */

interface AvatarApiService {
    /**
     * Returns Map<String, String> of [Avatar] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("users/{username}")
    suspend fun getAvatar(@Path(value = "username") username: String?): Map<String, Any?>
    //suspend fun getAvatar(@Url username: String): Map<String, String>
    //suspend fun getAvatar() : Map<String, String>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object AvatarApi {
    val retrofitService: AvatarApiService by lazy { retrofit.create(AvatarApiService::class.java) }
}

fun Map<String, Any?>.asAvatarData(): AvatarData {
    var user = ""
    var url = ""

    forEach{
        if (it.key == "login") { user = it.value as String}
        else if (it.key == "avatar_url") {url = it.value as String}
    }

    return AvatarData(avatarUrl = url, avatarUser = user)
}