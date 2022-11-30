package com.example.checkpoint2.data

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

//fun List<AvatarData>.asAvatar(): List<Avatar> { return map { (it.asAvatar()) } }

fun AvatarData.asAvatar(): Avatar { return Avatar(this.avatarUser , avatarUrl = this.avatarUrl)  }


fun Map<String, Any?>.asAvatarData(): AvatarData {
    var user = ""
    var url = ""

    forEach{
        if (it.key == "login") { user = it.value as String}
        else if (it.key == "avatar_url") {url = it.value as String}
    }

    return AvatarData(avatarUrl = url, avatarUser = user)
}