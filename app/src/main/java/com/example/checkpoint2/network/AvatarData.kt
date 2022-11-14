package com.example.checkpoint2.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.checkpoint2.model.Avatar

@Entity(tableName = "avatar")
data class AvatarData(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "avatar_name")
    val avatarUser: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String
)

fun AvatarData.asAvatar(): Avatar { return Avatar(this.avatarUser , avatarUrl = this.avatarUrl)  }
fun List<AvatarData>.asAvatar(): List<Avatar> { return map { Avatar(it.avatarUser , avatarUrl = it.avatarUrl)  } }
