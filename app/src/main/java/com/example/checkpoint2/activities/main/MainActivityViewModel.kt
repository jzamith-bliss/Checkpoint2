package com.example.checkpoint2.activities.main

import android.app.Application
import androidx.lifecycle.*
import com.example.checkpoint2.database.AvatarsRoomDatabase
import com.example.checkpoint2.repository.EmojiManager
import com.example.checkpoint2.database.EmojiRoomDatabase
import com.example.checkpoint2.database.ReposRoomDatabase
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.network.EmojiApi
import com.example.checkpoint2.network.asEmoji
import com.example.checkpoint2.network.asEmojiData
import com.example.checkpoint2.repository.AvatarManager
import com.example.checkpoint2.repository.ReposManager
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
    //val avatars:LiveData<List<Avatar>> = avatarRepository.avatars

    private val reposRepository = ReposManager(ReposRoomDatabase.getDatabase(application))

    private var _currentRandomEmoji = MutableLiveData<Emoji>()
    val currentRandomEmoji: LiveData<Emoji>
        get() = _currentRandomEmoji

    private var _usernameAvatar = MutableLiveData<Avatar>()
    val usernameAvatar: LiveData<Avatar>
        get() = _usernameAvatar

    fun initializeMainData() {
        viewModelScope.launch {
            _status.value = EmojiApiStatus.LOADING
            try {
                emojiRepository.refreshEmojis()
                //reposRepository.getReposApi("google")
                if (currentRandomEmoji.value == null) {
                    setNewRandomEmoji()
                }
                _status.value = EmojiApiStatus.DONE
            }
            catch (e: Exception) {
                e.printStackTrace()
                //_status.value = EmojiApiStatus.ERROR
                getFirstEmojiFromNetwork()

            }
        }
    }

    private suspend fun getFirstEmojiFromNetwork() {
        _currentRandomEmoji.value = EmojiApi.retrofitService.getEmojis().asEmojiData().asEmoji().random()
    }

    fun setNewRandomEmoji() {
        _currentRandomEmoji.value = emojis.value!![(emojis.value!!.indices).random()]
    }

    fun getGitHubUsername(username: String) {
        viewModelScope.launch {
            _usernameAvatar.value = avatarRepository.getAvatar(username.trim())
            //reposRepository.getReposApi(username)
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