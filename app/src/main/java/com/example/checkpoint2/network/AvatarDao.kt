package com.example.checkpoint2.network

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.network.AvatarData

@Dao
interface AvatarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(avatars: List<AvatarData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(avatar: AvatarData)

    @Delete
    suspend fun delete(avatar: AvatarData)

    @Query("SELECT * FROM avatar")
    fun getAvatars(): List<AvatarData>

    @Query("DELETE FROM avatar")
    fun clearAvatars()

    @Query("SELECT * FROM avatar WHERE avatar_name = :username")
    fun getSearchAvatar(username: String): AvatarData

    @Query("SELECT EXISTS (SELECT * FROM avatar WHERE avatar_name = :username)")
    fun exists(username: String): Boolean
}