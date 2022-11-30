package com.example.checkpoint2.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReposApiService {
    /**
     * Returns Map<String, String> of Repos and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String = "google",
        @Query("page") page: Int,
        @Query("limit") size: Int)
    : List<Map<String, Any?>>
}