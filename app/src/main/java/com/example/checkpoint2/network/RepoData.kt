package com.example.checkpoint2.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.checkpoint2.model.Repos


@Entity(tableName = "repos")
data class RepoData(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "full_name")
    val repoName: String,

)

//fun List<RepoData>.asRepo(): List<Repos> { return map { Repos(repoName = it.repoName) } }
fun RepoData.asRepo(): Repos { return Repos(repoName = repoName) }
fun List<RepoData>.asRepoList(): List<Repos> { return map { Repos(repoName = it.repoName) }}