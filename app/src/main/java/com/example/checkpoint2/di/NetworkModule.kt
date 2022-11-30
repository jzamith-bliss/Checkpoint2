package com.example.checkpoint2.di

import com.example.checkpoint2.network.AvatarApiService
import com.example.checkpoint2.network.EmojiApiService
import com.example.checkpoint2.network.ReposApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL =
        "https://api.github.com"


    @Singleton
    @Provides
    fun provideNetwork(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
                )
            )
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun provideEmojiApi(retrofit: Retrofit): EmojiApiService {
        return retrofit.create(EmojiApiService::class.java)
    }

    @Provides
    fun provideAvatarApi(retrofit: Retrofit): AvatarApiService {
        return retrofit.create(AvatarApiService::class.java)
    }

    @Provides
    fun provideReposApi(retrofit: Retrofit): ReposApiService {
        return retrofit.create(ReposApiService::class.java)
    }

}