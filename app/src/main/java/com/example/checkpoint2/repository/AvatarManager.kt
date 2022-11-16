package com.example.checkpoint2.repository

import com.example.checkpoint2.database.AvatarsRoomDatabase
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AvatarManager(private val database: AvatarsRoomDatabase) {

    suspend fun getAvatar(username: String): Avatar {
        return if (checkAvatarInDisk(username)) {getAvatarFromDisk(username)}
        else { getAvatarFromNetwork(username).also { insertAvatarInDiskFromNetwork(username)}}
    }

    private suspend fun checkAvatarInDisk(username: String): Boolean {
        return withContext(Dispatchers.IO) {
            database.avatarDao.exists(username)
        }
       //return getAvatarsData().any { it.avatarUser == username }
    }

    private suspend fun getAvatarFromDisk(username: String): Avatar {
        return getAvatarDataFromDisk(username).asAvatar()
    }

    private suspend fun getAvatarFromNetwork(username: String): Avatar {
        return getAvatarDataFromNetwork(username).asAvatar()
    }

    private suspend fun insertAvatarInDiskFromNetwork(username: String) {
        withContext(Dispatchers.IO) {
            database.avatarDao.insert(getAvatarDataFromNetwork(username))
        }
    }

    suspend fun getAvatars(): List<Avatar> {
        return withContext(Dispatchers.IO) {
            database.avatarDao.getAvatars().map { it.asAvatar() }
        }
    }

    private suspend fun getAvatarDataFromDisk(username: String): AvatarData {
        return withContext(Dispatchers.IO) {
            database.avatarDao.getSearchAvatar(username)
        }
    }

    suspend fun deleteAvatars(username: String) {
        return withContext(Dispatchers.IO) {
            database.avatarDao.delete(getAvatarDataFromDisk(username))
        }
    }

    private suspend fun getAvatarDataFromNetwork(username: String): AvatarData {
        return AvatarApi.retrofitService.getAvatar(username).asAvatarData()
    }

    suspend fun clearAvatars() {
        withContext(Dispatchers.IO) {
            database.avatarDao.clearAvatars()
        }
    }

}

