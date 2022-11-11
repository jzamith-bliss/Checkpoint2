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

    private var _persistentEmojiList: MutableList<Emoji> = mutableListOf()
    val persistentEmojiList: List<Emoji>
        get() = _persistentEmojiList


    fun initializeEmojiListData(onCompletion: () -> Unit ) {
        viewModelScope.launch {
            try {
                //emojiRepository.refreshEmojis()
                if (emojiList.isEmpty()) {
                    //setEmojiList()
                    _emojiList.addAll(EmojisNetwork.getEmojisNetwork())
                    _persistentEmojiList.addAll(EmojisNetwork.getEmojisNetwork())
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

    fun onEmojiItemClick(emoji: Emoji, onCompletion: () -> Unit) {
        //Toast.makeText(this, "Item ${emoji.emojiUrl} clicked", Toast.LENGTH_SHORT).show()
        _emojiList.remove(emoji)
        onCompletion()
    }

    fun refreshData() {
        _emojiList.clear()
        _emojiList.addAll(persistentEmojiList)
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