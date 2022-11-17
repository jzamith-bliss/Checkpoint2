package com.example.checkpoint2.activities.googleRepos

import android.app.Application
import androidx.lifecycle.*
import com.example.checkpoint2.database.ReposRoomDatabase
import com.example.checkpoint2.model.Repos
import com.example.checkpoint2.repository.ReposManager
import kotlinx.coroutines.launch

class GoogleReposViewModel(application: Application): AndroidViewModel(application) {

    private val reposRepository = ReposManager(ReposRoomDatabase.getDatabase(application))

    private var currentPage = 0
    private var currentSize = 20

    private var _repos = MutableLiveData<List<Repos>>(listOf())
    val repos: LiveData<List<Repos>>
        get() = _repos

    fun initializeRepositoryData() {
        getNextRepositories()
    }

    fun getRepositoriesUpdateSize(): Int {
        return currentSize
    }
    private var nextRepositoryPosition = 0

    fun getNextRepositoryPosition(): Int {
        return nextRepositoryPosition
    }

    private var updating = false
    fun isUpdating(): Boolean = updating
    private fun startUpdate() { updating = true }
    fun finishUpdate() { updating=  false }

    fun getNextRepositories() {
        startUpdate()
        viewModelScope.launch {
            currentPage++
            nextRepositoryPosition = (currentPage * currentSize)
            _repos.value = reposRepository.getRepos(currentPage, currentSize)
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