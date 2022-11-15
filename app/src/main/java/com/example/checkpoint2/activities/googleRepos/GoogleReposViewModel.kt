package com.example.checkpoint2.activities.googleRepos

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.checkpoint2.database.ReposRoomDatabase
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.model.Repos
import com.example.checkpoint2.network.RepoData
import com.example.checkpoint2.repository.ReposManager
import kotlinx.coroutines.launch

class GoogleReposViewModel(application: Application): AndroidViewModel(application) {

    private val reposRepository = ReposManager(ReposRoomDatabase.getDatabase(application))
/*
    private var _reposList = MutableLiveData<Repos>()
    val reposList: LiveData<Repos>
        get() = _reposList*/

    private var _list: MutableList<Repos> = mutableListOf()
    val list: List<Repos>
        get() = _list


    fun initializeReposData(onCompletion: () -> Unit ) {
        viewModelScope.launch {
            try {
                if (list.isEmpty()) {
                    //_reposList.value = reposRepository.getReposFromDisk()
                    //_list.addAll(reposRepository.getListReposFromDisk())
                    _list.addAll(reposRepository.getRepos("google"))
                    Log.v("LIST", "${list.size}")
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
            if (modelClass.isAssignableFrom(GoogleReposViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GoogleReposViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }

}