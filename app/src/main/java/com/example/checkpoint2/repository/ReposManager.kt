package com.example.checkpoint2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.example.checkpoint2.database.EmojiRoomDatabase
import com.example.checkpoint2.database.ReposRoomDatabase
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.model.Repos
import com.example.checkpoint2.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class ReposManager(private val database: ReposRoomDatabase) {

 /*   suspend fun getReposApi(username: String) {
        withContext(Dispatchers.IO) {
            val reposList = ReposApi.retrofitService.getRepos(username)
            database.reposDao.insertAll(reposList.asReposData())
        }
    }*/

    suspend fun getRepos(username: String): List<Repos> {
        return if (checkReposInDisk(username)) {getReposFromDisk(username)}
        else { getReposFromNetwork(username).also { insertReposInDiscFromNetwork(username)}}
    }

    private suspend fun checkReposInDisk(username: String): Boolean {
        return withContext(Dispatchers.IO) {
            database.reposDao.exists(username)
        }
    }

    private suspend fun getReposFromDisk(username: String): List<Repos> {
        return listOf(getReposDataFromDisk(username).asRepo())
    }

    private suspend fun getReposFromNetwork(username: String): List<Repos> {
        return getReposDataFromNetwork(username).asRepoList()
    }

    private suspend fun insertReposInDiscFromNetwork(username: String) {
        withContext(Dispatchers.IO) {
            database.reposDao.insertAll(getReposDataFromNetwork(username))
        }
    }

    suspend fun getListReposFromDisk(): List<Repos> {
        return withContext(Dispatchers.IO) {
            database.reposDao.getReposList().asRepoList()
        }
    }

    suspend fun getReposDataFromDisk(username: String): RepoData {
        return withContext(Dispatchers.IO) {
            database.reposDao.getSearchRepos(username)
        }
    }

    suspend fun getReposDataFromNetwork(username: String): List<RepoData> {
        return ReposApi.retrofitService.getRepos(username).asReposData()
    }


}

object RepoNetwork {
    suspend fun getReposNetwork(username: String): List<Repos> {
        return ReposApi.retrofitService.getRepos(username).asReposData().asRepoList()//.toMutableList()
    }
}
