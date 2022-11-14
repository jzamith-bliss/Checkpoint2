package com.example.checkpoint2.repository

import com.example.checkpoint2.database.AvatarsRoomDatabase
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AvatarManager(private val database: AvatarsRoomDatabase) {

   /* val avatars: LiveData<List<Avatar>> =
        Transformations.map(database.avatarDao.getAvatars()) { it.asAvatar() }
*/
    suspend fun refreshAvatars(username: String) {
        withContext(Dispatchers.IO) {
            val avatarList = AvatarApi.retrofitService.getAvatar(username)
            database.avatarDao.insertAll(listOf(avatarList.asAvatarData()))
        }
    }

    suspend fun clearAvatars() {
        withContext(Dispatchers.IO) {
            database.avatarDao.clearAvatars()
        }
    }
/*
    suspend fun getAvatarsFromDisk(): List<Avatar> {
        return database.avatarDao.getAvatars().map { it.asAvatar() }
    }*/


    suspend fun getAvatars(): List<Avatar> {
        return withContext(Dispatchers.IO) {
            database.avatarDao.getAvatars().map { it.asAvatar() }
        }
    }

    private suspend fun getAvatarDataFromDisk(username: String):AvatarData {
        return withContext(Dispatchers.IO) {
            database.avatarDao.getSearchAvatar(username)
        }
    }

    suspend fun deleteAvatars(username: String) {
        return withContext(Dispatchers.IO) {
            database.avatarDao.delete(getAvatarDataFromDisk(username))
        }
    }
}

object AvatarsNetwork {
    suspend fun getAvatarsNetwork(string: String): Avatar {
        return AvatarApi.retrofitService.getAvatar(string).asAvatarData().asAvatar()//.toMutableList()
    }
}
