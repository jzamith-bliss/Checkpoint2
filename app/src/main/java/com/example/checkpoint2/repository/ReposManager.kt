package com.example.checkpoint2.repository

import com.example.checkpoint2.database.ReposRoomDatabase
import com.example.checkpoint2.model.Repos
import com.example.checkpoint2.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReposManager(private val database: ReposRoomDatabase) {

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


/*    private suspend fun getNextRepositoriesFromDisk(page: Int, size: Int): List<Repos> {
        return withContext(Dispatchers.IO) {
            database.reposDao.getNextRepos(page, size).asRepoList()
        }
    }*/

    private suspend fun getNextRepositoriesFromDisk(page: Int, size: Int): List<Repos> {
        return getNextReposDataFromDisk(page, size).asRepoList()
    }

    private suspend fun getNextReposDataFromDisk( page: Int, size: Int): List<RepoData> {
        return withContext(Dispatchers.IO) {
            database.reposDao.getNextRepos((page * size))
        }
    }

    private suspend fun getNextRepositoriesFromNetwork( page: Int, size: Int): List<Repos> {
        return getReposDataFromNetwork(page, size).asRepoList()
    }

    suspend fun getReposDataFromNetwork( page: Int, size: Int): List<RepoData> {
        return ReposApi.retrofitService.getRepos("google", page, size).asReposData()
    }

    private suspend fun getNumberOfRepositories(): Int {
        return withContext(Dispatchers.IO) {
            database.reposDao.getReposNum()
        }
    }


    private suspend fun getReposFromDisk(): List<Repos> {
        return getReposDataFromDisk().asRepoList()
    }

    suspend fun getReposDataFromDisk(): List<RepoData> {
        return withContext(Dispatchers.IO) {
            database.reposDao.getRepos()
        }
    }



    private suspend fun insertReposInDiscFromNetwork(page: Int, size: Int) {
        withContext(Dispatchers.IO) {
            database.reposDao.insertAll(getReposDataFromNetwork(page, size))
        }
    }

/*    suspend fun getListReposFromDisk(): List<Repos> {
        return withContext(Dispatchers.IO) {
            database.reposDao.getReposList().asRepoList()
        }
    }


    suspend fun clearRepos() {
        withContext(Dispatchers.IO) {
            database.reposDao.clearRepos()
        }
    }*/
}

