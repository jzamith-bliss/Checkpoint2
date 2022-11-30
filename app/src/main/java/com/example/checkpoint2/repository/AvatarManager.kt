package com.example.checkpoint2.repository

import com.example.checkpoint2.data.AvatarData
import com.example.checkpoint2.data.asAvatar
import com.example.checkpoint2.data.asAvatarData
import com.example.checkpoint2.database.AvatarDao
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AvatarManager @Inject constructor(
    private val avatarDao: AvatarDao,
    private val avatarApiService: AvatarApiService
    ) {

    suspend fun getAvatar(username: String): Avatar {
        return if (checkAvatarInDisk(username)) {getAvatarFromDisk(username)}
        else { getAvatarFromNetwork(username).also { insertAvatarInDiskFromNetwork(username)}}
    }

    private suspend fun checkAvatarInDisk(username: String): Boolean {
        return withContext(Dispatchers.IO) {
            avatarDao.exists(username)
        }
    }

    private suspend fun getAvatarFromDisk(username: String): Avatar {
        return getAvatarDataFromDisk(username).asAvatar()
    }

    private suspend fun getAvatarFromNetwork(username: String): Avatar {
        return getAvatarDataFromNetwork(username).asAvatar()
    }

    private suspend fun insertAvatarInDiskFromNetwork(username: String) {
        withContext(Dispatchers.IO) {
            avatarDao.insert(getAvatarDataFromNetwork(username))
        }
    }

    suspend fun getAvatars(): List<Avatar> {
        return withContext(Dispatchers.IO) {
            avatarDao.getAvatars().map { it.asAvatar() }
        }
    }

    private suspend fun getAvatarDataFromDisk(username: String): AvatarData {
        return withContext(Dispatchers.IO) {
            avatarDao.getSearchAvatar(username)
        }
    }

    suspend fun deleteAvatars(username: String) {
        return withContext(Dispatchers.IO) {
            avatarDao.delete(getAvatarDataFromDisk(username))
        }
    }

    private suspend fun getAvatarDataFromNetwork(username: String): AvatarData {
        return avatarApiService.getAvatar(username).asAvatarData()
    }

    suspend fun clearAvatars() {
        withContext(Dispatchers.IO) {
            avatarDao.clearAvatars()
        }
    }

    suspend fun getAvatarByUrlFromDatabase(url: String): Avatar {
        return withContext(Dispatchers.IO) {
            avatarDao.getAvatarByUrl(url).asAvatar()
        }
    }

}

