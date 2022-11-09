package com.example.checkpoint2.activities.main


import android.app.Application
import androidx.lifecycle.*
import com.example.checkpoint2.repository.EmojiRepository
import com.example.checkpoint2.database.EmojiRoomDatabase
import com.example.checkpoint2.model.Emoji
import kotlinx.coroutines.launch
import java.io.IOException


class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    private val emojiRepository = EmojiRepository(EmojiRoomDatabase.getDatabase(application))
    val emojis:LiveData<List<Emoji>> = emojiRepository.emojis

    private var _currentRandomEmoji = MutableLiveData<Emoji>()
    val currentRandomEmoji: LiveData<Emoji>
        get() = _currentRandomEmoji

    fun initializeMainData() {
        viewModelScope.launch {
            try {
                emojiRepository.refreshEmojis()
                randomImg()
            }
            catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun randomImg() {
        _currentRandomEmoji.value = emojis.value!![(emojis.value!!.indices).random()]
        //val randEmoji = emojis.value?.random()
        //_currentRandomEmoji.value = emojis.value?.indexOf(randEmoji)
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
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}