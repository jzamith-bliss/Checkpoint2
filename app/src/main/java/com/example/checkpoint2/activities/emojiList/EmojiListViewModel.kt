package com.example.checkpoint2.activities.emojiList

import android.app.Application
import androidx.lifecycle.*

import com.example.checkpoint2.database.EmojiRoomDatabase
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.network.EmojisNetwork
import com.example.checkpoint2.repository.EmojiRepository
import kotlinx.coroutines.launch

class EmojiListViewModel(application: Application): AndroidViewModel(application) {

    private val emojiRepository = EmojiRepository(EmojiRoomDatabase.getDatabase(application))
    val databaseEmojis:LiveData<List<Emoji>> = emojiRepository.emojis

    private var _emojiList: MutableList<Emoji> = mutableListOf()
    val emojiList: List<Emoji>
        get() = _emojiList


    fun initializeEmojiListData(onCompletion: () -> Unit ) {
        viewModelScope.launch {
            try {
                //emojiRepository.refreshEmojis()
                if (emojiList.isEmpty()) {
                    //setEmojiList()
                    _emojiList.addAll(EmojisNetwork.getEmojisNetwork())
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