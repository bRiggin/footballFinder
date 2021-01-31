package com.briggin.footballfinder.data

import com.briggin.footballfinder.api.retorfit.dto.ApiError
import com.briggin.footballfinder.api.retorfit.dto.Success
import com.briggin.footballfinder.domain.*

class FootballRepository(
    private val localStorage: FootballStorage,
    private val remoteApi: FootballApi,
    private val mapper: DataSourceMapper,
    private val cache: QueryCache
) : FootballDataSource {

    override suspend fun performSearch(query: String): Result {
        cache.query = query
        updatePlayersWithRemoteApi(cache.currentPlayersIndex())
        updateTeamsWithTeamsApi(cache.currentTeamsIndex())
        return getLocalData()
    }

    override suspend fun getMorePlayers(): Result {
        updatePlayersWithRemoteApi(cache.nextPlayersIndex())
        return getLocalData()
    }

    override suspend fun getMoreTeams(): Result {
        updateTeamsWithTeamsApi(cache.nextTeamsIndex())
        return getLocalData()
    }

    override suspend fun likePlayer(id: String): Result {
        localStorage.likePlayer(id)
        return getLocalData()
    }

    override suspend fun unlikePlayer(id: String): Result {
        localStorage.unlikePlayer(id)
        return getLocalData()
    }

    private suspend fun updatePlayersWithRemoteApi(index: Long) {
        when (val response = remoteApi.getPlayers(cache.query, index)) {
            is Success -> localStorage.updatePlayers(response.items)
            is ApiError -> cache.cacheError(ResultError.Players)
        }
    }

    private suspend fun updateTeamsWithTeamsApi(index: Long) {
        when (val response = remoteApi.getTeams(cache.query, index)) {
            is Success -> localStorage.updateTeams(response.items)
            is ApiError -> cache.cacheError(ResultError.Teams)
        }
    }

    private suspend fun getLocalData() = Result(
        mapper.mapPlayerResponse(localStorage.getPlayers(cache.query, cache.currentPlayersIndex())),
        mapper.mapTeamResponse(localStorage.getTeams(cache.query, cache.currentTeamsIndex())),
        cache.getErrors()
    )
}
