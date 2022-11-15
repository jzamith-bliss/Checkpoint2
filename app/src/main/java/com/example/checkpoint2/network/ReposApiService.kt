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
 * A public interface that exposes the [getRepos] method
 */

interface ReposApiService {
    /**
     * Returns Map<String, String> of [Repos] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): List<Map<String, Any?>>
    //suspend fun getAvatar(@Url username: String): Map<String, String>
    //suspend fun getAvatar() : Map<String, String>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ReposApi {
    val retrofitService: ReposApiService by lazy { retrofit.create(ReposApiService::class.java) }
}

fun List<Map<String, Any?>>.asReposData(): List<RepoData> {
    //return map { RepoData( name = it.key, repoName = it.value) }
    return map {
        RepoData( repoName = (it["full_name"] as String))
    }
}