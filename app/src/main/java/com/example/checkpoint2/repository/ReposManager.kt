package com.example.checkpoint2.repository

import com.example.checkpoint2.data.RepoData
import com.example.checkpoint2.data.asRepoList
import com.example.checkpoint2.data.asReposData
import com.example.checkpoint2.database.ReposDao
import com.example.checkpoint2.model.Repos
import com.example.checkpoint2.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReposManager  @Inject constructor(
    private val reposDao: ReposDao,
    private val reposApiService: ReposApiService
    ) {

    suspend fun getRepos(page: Int, size: Int): List<Repos> {

        val repositories: MutableList<Repos> = mutableListOf()

        if (getNumberOfRepositories() >= (page * size)) {
            repositories.addAll(getNextRepositoriesFromDisk(page, size))
        } else {
            repositories.addAll(getReposFromDisk())
            repositories.addAll(getNextRepositoriesFromNetwork(page, size))
            insertReposInDiscFromNetwork(page, size)
        }

        return repositories.toList()
    }

    private suspend fun getNextRepositoriesFromDisk(page: Int, size: Int): List<Repos> {
        return getNextReposDataFromDisk(page, size).asRepoList()
    }

    private suspend fun getNextReposDataFromDisk( page: Int, size: Int): List<RepoData> {
        return withContext(Dispatchers.IO) {
            reposDao.getNextRepos((page * size))
        }
    }

    private suspend fun getNextRepositoriesFromNetwork( page: Int, size: Int): List<Repos> {
        return getReposDataFromNetwork(page, size).asRepoList()
    }

    private suspend fun getReposDataFromNetwork(page: Int, size: Int): List<RepoData> {
        return reposApiService.getRepos("google", page, size).asReposData()
    }

    private suspend fun getNumberOfRepositories(): Int {
        return withContext(Dispatchers.IO) {
            reposDao.getReposNum()
        }
    }

    private suspend fun getReposFromDisk(): List<Repos> {
        return getReposDataFromDisk().asRepoList()
    }

    private suspend fun getReposDataFromDisk(): List<RepoData> {
        return withContext(Dispatchers.IO) {
            reposDao.getRepos()
        }
    }

    private suspend fun insertReposInDiscFromNetwork(page: Int, size: Int) {
        withContext(Dispatchers.IO) {
            reposDao.insertAll(getReposDataFromNetwork(page, size))
        }
    }

/*    suspend fun clearRepos() {
        withContext(Dispatchers.IO) {
            reposDao.clearRepos()
        }
    }*/
}

