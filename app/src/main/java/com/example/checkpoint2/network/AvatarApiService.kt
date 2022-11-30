package com.example.checkpoint2.network

import retrofit2.http.GET
import retrofit2.http.Path


interface AvatarApiService {
    /**
     * Returns Map<String, String> of Avatar and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("users/{username}")
    suspend fun getAvatar(@Path(value = "username") username: String?): Map<String, Any?>
    //suspend fun getAvatar(@Url username: String): Map<String, String>
    //suspend fun getAvatar() : Map<String, String>
}

