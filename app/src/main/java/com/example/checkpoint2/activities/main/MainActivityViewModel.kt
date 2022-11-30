package com.example.checkpoint2.activities.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.checkpoint2.repository.EmojiManager
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.repository.AvatarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class EmojiApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val emojiRepository: EmojiManager,
    private val avatarRepository: AvatarManager
    ): ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<EmojiApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<EmojiApiStatus> = _status

    val emojis:LiveData<List<Emoji>> = emojiRepository.emojis

    private var _currentRandomEmoji = MutableLiveData<Emoji>()
    val currentRandomEmoji: LiveData<Emoji>
        get() = _currentRandomEmoji

    private var _usernameAvatar = MutableLiveData<Avatar>()
    val usernameAvatar: LiveData<Avatar>
        get() = _usernameAvatar


    fun initializeMainData(emoji: String?, avatar: String?) {
        viewModelScope.launch {
            _status.value = EmojiApiStatus.LOADING
            try {
                if (emoji == null && avatar != null) {
                    _usernameAvatar.value = avatarRepository.getAvatarByUrlFromDatabase(avatar)
                } else if (avatar == null && emoji != null) {
                    _currentRandomEmoji.value = emojiRepository.getEmojiByUrlFromDatabase(emoji)
                } else {
                    _currentRandomEmoji.value = emojiRepository.getEmojis().random()
                }

                _status.value = EmojiApiStatus.DONE
            }
            catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }

    fun setNewRandomEmoji() {
        _currentRandomEmoji.value = emojis.value!![(emojis.value!!.indices).random()]
    }

    fun getGitHubUsername(username: String, context: Context) {
        viewModelScope.launch {
            try {
                _usernameAvatar.value = avatarRepository.getAvatar(username)
            } catch (e: Exception) {
                Toast.makeText(context, "User not found!", Toast.LENGTH_LONG).show()
            }

        }
    }
}