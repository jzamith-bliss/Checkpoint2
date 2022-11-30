package com.example.checkpoint2.di

import android.content.Context
import com.example.checkpoint2.CheckpointApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): CheckpointApplication{
        return app as CheckpointApplication
    }

    @Singleton
    @Provides
    fun provideRandomString(): String{
        return "Hey look a wild random string appeared!!! "
    }

}