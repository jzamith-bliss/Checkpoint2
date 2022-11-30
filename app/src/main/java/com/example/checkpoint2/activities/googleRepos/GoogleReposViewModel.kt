package com.example.checkpoint2.activities.googleRepos

import androidx.lifecycle.*
import com.example.checkpoint2.model.Repos
import com.example.checkpoint2.repository.ReposManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleReposViewModel @Inject constructor(private val reposRepository: ReposManager): ViewModel() {

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
}