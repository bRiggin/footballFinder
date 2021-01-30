package com.briggin.footballfinder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.briggin.footballfinder.domain.FootballDataSource
import com.briggin.footballfinder.domain.Result

class FootballViewModel(
    private val dataSource: FootballDataSource,
    private val mapper: ModelMapper
) : ViewModel() {

    private val _state: MutableLiveData<List<FootballModel>> = MutableLiveData()
    val state: LiveData<List<FootballModel>> = _state

    suspend fun performSearch(query: String) {
        loadFootballData { dataSource.performSearch(query) }
    }

    suspend fun loadMorePlayers() {
        _state.value?.let { currentState ->
            val loadingState = currentState.replaceWith(Loader) { it.type == ModelType.LoadMorePlayers }
            _state.postValue(loadingState)
            loadFootballData { dataSource.getMorePlayers() }
        }
    }

    suspend fun loadMoreTeams() {
        _state.value?.let { currentState ->
            val loadingState = currentState.replaceWith(Loader) { it.type == ModelType.LoadMoreTeams}
            _state.postValue(loadingState)
            loadFootballData { dataSource.getMoreTeams() }
        }
    }

    private suspend fun loadFootballData(block: suspend () -> Result) {
        _state.postValue(mapper.mapToModels(block()))
    }
}

private fun <T> List<T>.replaceWith(newValue: T, block: (T) -> Boolean): List<T> =
    map { if (block(it)) newValue else it }
