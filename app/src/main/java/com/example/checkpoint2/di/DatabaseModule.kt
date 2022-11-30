package com.example.checkpoint2.di


import android.content.Context
import androidx.room.Room
import com.example.checkpoint2.database.RoomDatabase
import com.example.checkpoint2.database.AvatarDao
import com.example.checkpoint2.database.EmojiDao
import com.example.checkpoint2.database.ReposDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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