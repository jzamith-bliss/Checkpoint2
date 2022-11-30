package com.example.checkpoint2.activities.emojiList

import androidx.lifecycle.*

import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.repository.EmojiManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmojiListViewModel @Inject constructor(private val emojiRepository: EmojiManager): ViewModel() {

    private var _emojiList: MutableList<Emoji> = mutableListOf()
    val emojiList: List<Emoji>
        get() = _emojiList

    private var _persistentEmojiList: MutableList<Emoji> = mutableListOf()
    val persistentEmojiList: List<Emoji>
        get() = _persistentEmojiList


    fun initializeEmojiListData(onCompletion: () -> Unit ) {
        viewModelScope.launch {
            try {
                _emojiList.addAll(emojiRepository.getEmojis())
                _persistentEmojiList.addAll(emojiRepository.getEmojis())
                onCompletion()
            }
            catch (e: Exception) {
                e.printStackTrace()
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
}