package com.example.checkpoint2.network

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.checkpoint2.model.Repos

@Dao
interface ReposDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(repos: List<RepoData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(repos: RepoData)

    @Delete
    suspend fun delete(repos: RepoData)

    @Query("SELECT COUNT(*) FROM repos")
    fun getReposNum(): Int

    @Query("SELECT * FROM repos LIMIT :limit")
    fun getNextRepos(limit: Int): List<RepoData>

    @Query("SELECT * FROM repos")
    fun getRepos(): List<RepoData>

    @Query("SELECT * FROM repos")
    fun getReposList(): List<RepoData>

    @Query("SELECT * FROM repos")
    fun getReposLiveData(): LiveData<List<RepoData>>

    @Query("DELETE FROM repos")
    fun clearRepos()

    @Query("SELECT * FROM repos WHERE full_name = :username")
    fun getSearchRepos(username: String): RepoData

    @Query("SELECT EXISTS (SELECT * FROM repos WHERE full_name = :username)")
    fun exists(username: String): Boolean

}