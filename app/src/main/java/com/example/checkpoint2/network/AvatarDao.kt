package com.example.checkpoint2.network

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AvatarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(avatars: List<AvatarData>)

    @Delete
    suspend fun delete(avatar: AvatarData)

    @Query("SELECT * FROM avatar")
    fun getAvatars(): LiveData<List<AvatarData>>

    @Query("DELETE FROM avatar")
    fun clearAvatars()

}