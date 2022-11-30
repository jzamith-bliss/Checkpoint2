package com.example.checkpoint2.di


import android.content.Context
import androidx.room.Room
import com.example.checkpoint2.database.RoomDatabase
import com.example.checkpoint2.network.AvatarDao
import com.example.checkpoint2.network.EmojiDao
import com.example.checkpoint2.network.ReposDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RoomDatabase{
        return Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            "room_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideEmojiDao(database: RoomDatabase): EmojiDao {
        return database.emojiDao()
    }

    @Provides
    fun provideAvatarDao(database: RoomDatabase): AvatarDao {
        return database.avatarDao()
    }

    @Provides
    fun provideReposDao(database: RoomDatabase): ReposDao {
        return database.reposDao()
    }
}