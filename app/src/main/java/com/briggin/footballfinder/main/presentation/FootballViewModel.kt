package com.briggin.footballfinder.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.briggin.footballfinder.R
import com.briggin.footballfinder.common.domain.FootballDataSource
import com.briggin.footballfinder.common.domain.Result
import com.briggin.footballfinder.common.domain.ResultError
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class FootballViewModel(
    private val dataSource: FootballDataSource,
    private val mapper: ModelMapper
) : ViewModel() {

    @ExperimentalCoroutinesApi
    private val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    @FlowPreview
    @ExperimentalCoroutinesApi
    private val debouncedQuery = queryChannel
        .asFlow()
        .debounce(400)
        .mapLatest { it.trim() }
        .distinctUntilChanged()
        .flowOn(Dispatchers.Default)

    private val _state: MutableLiveData<List<FootballModel>> = MutableLiveData()
    val state: LiveData<List<FootballModel>> = _state

    private val _error: MutableLiveData<Int> = MutableLiveData()
    val error: LiveData<Int> = _error

    init {
        CoroutineScope(Dispatchers.Default).launch {
            debouncedQuery.collect { query -> performQuery(query) }
        }
    }

    @ExperimentalCoroutinesApi
    fun newSearch(query: String) {
        queryChannel.offer(query)
    }

    suspend fun loadMorePlayers() {
        _state.value?.let { currentState ->
            val loadingState =
                currentState.replaceWith(Loading) { it.type == ModelType.LoadMorePlayers }
            _state.postValue(loadingState)
            loadFootballData { dataSource.getMorePlayers() }
        }
    }

    suspend fun loadMoreTeams() {
        _state.value?.let { currentState ->
            val loadingState =
                currentState.replaceWith(Loading) { it.type == ModelType.LoadMoreTeams }
            _state.postValue(loadingState)
            loadFootballData { dataSource.getMoreTeams() }
        }
    }

    suspend fun onLikeAction(player: Player) {
        loadFootballData {
            if (player.isFavourite) dataSource.unlikePlayer(player.id)
            else dataSource.likePlayer(player.id)
        }
    }

    private suspend fun performQuery(query: String) {
        _state.postValue(
            listOf(Header(R.string.header_players), Loading, Header(R.string.header_teams), Loading)
        )
        loadFootballData { dataSource.performSearch(query) }
    }

    private suspend fun loadFootballData(block: suspend () -> Result) {
        val result = block()
        _state.postValue(mapper.mapToModels(result))

        val error = when {
            result.remoteErrors.contains(ResultError.Teams) && result.remoteErrors.contains(ResultError.Players) ->
                R.string.error_both
            result.remoteErrors.contains(ResultError.Teams) -> R.string.error_teams
            result.remoteErrors.contains(ResultError.Players) -> R.string.error_players
            else -> null
        }
        error?.let { _error.postValue(it) }
    }
}

private fun <T> List<T>.replaceWith(newValue: T, block: (T) -> Boolean): List<T> =
    map { if (block(it)) newValue else it }
