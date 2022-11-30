package com.example.checkpoint2.activities.avatarsList

import androidx.lifecycle.*
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.repository.AvatarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvatarListViewModel @Inject constructor(private val avatarRepository: AvatarManager): ViewModel() {

    private var _avatarList: MutableList<Avatar> = mutableListOf()
    val avatarList: List<Avatar>
        get() = _avatarList

    fun initializeAvatarListData(onCompletion: () -> Unit ) {
        viewModelScope.launch {
            try {
                if (avatarList.isEmpty()) {
                    _avatarList.addAll(avatarRepository.getAvatars())
                    onCompletion()
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
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