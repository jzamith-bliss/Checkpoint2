package com.example.checkpoint2.activities.main

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.checkpoint2.database.AvatarsRoomDatabase
import com.example.checkpoint2.repository.EmojiManager
import com.example.checkpoint2.database.EmojiRoomDatabase
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.network.asAvatar
import com.example.checkpoint2.repository.AvatarManager
import kotlinx.coroutines.launch


enum class EmojiApiStatus { LOADING, ERROR, DONE }

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<EmojiApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<EmojiApiStatus> = _status

    private val emojiRepository = EmojiManager(EmojiRoomDatabase.getDatabase(application))
    val emojis:LiveData<List<Emoji>> = emojiRepository.emojis

    private val avatarRepository = AvatarManager(AvatarsRoomDatabase.getDatabase(application))

    private var _currentRandomEmoji = MutableLiveData<Emoji>()
    val currentRandomEmoji: LiveData<Emoji>
        get() = _currentRandomEmoji

    private var _usernameAvatar = MutableLiveData<Avatar>()
    val usernameAvatar: LiveData<Avatar>
        get() = _usernameAvatar

    //val sharedPreferences = getSharedPreferences("mainImage", MODE_PRIVATE)
    //SharedPreferences sharedPreferences = getApplication().getSharedPreferences("mainImage", Context.MODE_PRIVATE)

    fun initializeMainData(emoji: String?, avatar: String?) {
        viewModelScope.launch {
            _status.value = EmojiApiStatus.LOADING
            try {
                Log.v("LOG", "emoji $emoji avatar $avatar")
                if (emoji == null && avatar !== null) {
                    Log.v("LOG", "avatar $avatar")
                    _usernameAvatar.value = avatarRepository.getAvatarByUrlFromDatabase(avatar)
                } else if (avatar == null && emoji !== null) {
                    Log.v("LOG", "emoji $emoji")
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

    /**
     * Factory for constructing DevByteViewModel with parameter
     */

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }

}