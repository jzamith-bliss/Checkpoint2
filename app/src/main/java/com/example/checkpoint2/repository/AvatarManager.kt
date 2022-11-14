package com.example.checkpoint2.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.checkpoint2.database.AvatarsRoomDatabase
import com.example.checkpoint2.database.EmojiRoomDatabase
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AvatarManager(private val database: AvatarsRoomDatabase) {

    val avatars: LiveData<List<AvatarData>> =
        Transformations.map(database.avatarDao.getAvatars()) { it }

    suspend fun refreshAvatars(username: String) {
        withContext(Dispatchers.IO) {
            val avatarList = AvatarApi.retrofitService.getAvatar(username)
            database.avatarDao.insertAll(listOf(avatarList.asAvatarData()))
        }
    }

    suspend fun getAvatars() {
        withContext(Dispatchers.IO) {
            database.avatarDao.getAvatars()
        }
    }
}

object AvatarsNetwork {
    suspend fun getAvatarsNetwork(string: String): Avatar {
        return AvatarApi.retrofitService.getAvatar(string).asAvatarData().asAvatar()//.toMutableList()
    }
}
