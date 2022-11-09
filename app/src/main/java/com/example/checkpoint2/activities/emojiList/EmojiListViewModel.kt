package com.example.checkpoint2.activities.emojiList

import android.app.Application
import androidx.lifecycle.*
import com.example.checkpoint2.database.EmojiRoomDatabase
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.repository.EmojiRepository
import kotlinx.coroutines.launch

enum class EmojiApiStatus { LOADING, ERROR, DONE }

class EmojiListViewModel(emojiListApplication: Application): AndroidViewModel(emojiListApplication) {

    private val emojiRepository = EmojiRepository(EmojiRoomDatabase.getDatabase(emojiListApplication))
    val emojis:LiveData<List<Emoji>> = emojiRepository.emojis

    private var _currentEmojiList: LiveData<List<Emoji>> =
        emojis
    val currentEmojiList: LiveData<List<Emoji>>
        get() = _currentEmojiList


    fun onEmojiItemClick(position: Int) {

        //_currentEmojiList.removeAt(position)
    }

    fun refreshData() {
        //_currentEmojiList.clear()
        //_currentEmojiList.addAll(myDataset)
    }

    fun initializeMainData() {
        viewModelScope.launch {
            try {
                emojiRepository.refreshEmojis()
            }
            catch (e: Exception) { e.printStackTrace() }
        }
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EmojiListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EmojiListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}