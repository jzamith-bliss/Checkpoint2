package com.example.checkpoint2.activities.main


import android.app.Application
import androidx.lifecycle.*
import com.example.checkpoint2.repository.EmojiRepository
import com.example.checkpoint2.database.EmojiRoomDatabase
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.network.EmojiApi
import com.example.checkpoint2.network.asEmoji
import com.example.checkpoint2.network.asEmojiData
import kotlinx.coroutines.launch


enum class EmojiApiStatus { LOADING, ERROR, DONE }

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<EmojiApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<EmojiApiStatus> = _status

    private val emojiRepository = EmojiRepository(EmojiRoomDatabase.getDatabase(application))
    val emojis:LiveData<List<Emoji>> = emojiRepository.emojis

    private var _currentRandomEmoji = MutableLiveData<Emoji>()
    val currentRandomEmoji: LiveData<Emoji>
        get() = _currentRandomEmoji


    fun initializeMainData() {
        viewModelScope.launch {
            _status.value = EmojiApiStatus.LOADING
            try {
                emojiRepository.refreshEmojis()
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