package com.example.checkpoint2.activities.avatarsList

import android.app.Application
import androidx.lifecycle.*
import com.example.checkpoint2.database.AvatarsRoomDatabase
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.repository.AvatarManager
import kotlinx.coroutines.launch

class AvatarListViewModel(application: Application): AndroidViewModel(application) {

    private val avatarRepository = AvatarManager(AvatarsRoomDatabase.getDatabase(application))
    //val avatars: LiveData<List<Avatar>> = avatarRepository.avatars

    private var _avatarList: MutableList<Avatar> = mutableListOf()
    val avatarList: List<Avatar>
        get() = _avatarList

    fun initializeAvatarListData(onCompletion: () -> Unit ) {
        viewModelScope.launch {
            try {
                //_avatarList = avatarRepository.getAvatarsFromDisk()
                //_avatarList.addAll(avatarRepository.getAvatars())
                if (avatarList.isEmpty()) {
                    //setEmojiList()
                    //getAvatars()
                    _avatarList.addAll(avatarRepository.getAvatars())
                    onCompletion()
                    //getEmojiListFromNetwork()
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                //_status.value = EmojiApiStatus.ERROR
            }
        }
    }


    fun onAvatarItemClick(avatar: Avatar, onCompletion: () -> Unit) {
        viewModelScope.launch {
            avatarRepository.deleteAvatars(avatar.id)
            _avatarList.remove(avatar)
            onCompletion()
        }
    }

    fun clearAvatars() {
        viewModelScope.launch {
            avatarRepository.clearAvatars()
        }
    }
}