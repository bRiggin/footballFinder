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
        updatePlayersWithRemoteApi()
        updateTeamsWithTeamsApi()
        return getLocalData()
    }

    override suspend fun getMorePlayers(): Result {
        updatePlayersWithRemoteApi()
        return getLocalData()
    }

    override suspend fun getMoreTeams(): Result {
        updateTeamsWithTeamsApi()
        return getLocalData()
    }

    private suspend fun updatePlayersWithRemoteApi() {
        when (val response = remoteApi.getPlayers(cache.query, cache.nextPlayersIndex())) {
            is Success -> localStorage.updatePlayers(response.items)
            is ApiError -> cache.cacheError(ResultError.Players)
        }
    }

    private suspend fun updateTeamsWithTeamsApi() {
        when (val response = remoteApi.getTeams(cache.query, cache.nextTeamsIndex())) {
            is Success -> localStorage.updateTeams(response.items)
            is ApiError -> cache.cacheError(ResultError.Teams)
        }
    }

    private suspend fun getLocalData() = Result(
        mapper.mapPlayerResponse(localStorage.getPlayers(cache.query)),
        mapper.mapTeamResponse(localStorage.getTeams(cache.query)),
        cache.getErrors()
    )
}
