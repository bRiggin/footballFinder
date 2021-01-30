package com.briggin.footballfinder.data

import com.briggin.footballfinder.api.dto.Success
import com.briggin.footballfinder.domain.*
import kotlinx.coroutines.*

class FootballRepository(
    private val localStorage: FootballStorage,
    private val remoteApi: FootballApi,
    private val mapper: DataSourceMapper,
    private val cache: QueryCache
) : FootballDataSource {

    override suspend fun performSearch(query: String): Result {
        cache.setCache(query)
        updatePlayersWithRemoteApi()
        updateTeamsWithTeamsApi()
        return returnLocalData()
    }

    override suspend fun getMorePlayers(): Result {
        updatePlayersWithRemoteApi()
        return returnLocalData()
    }

    override suspend fun getMoreTeams(): Result {
        updateTeamsWithTeamsApi()
        return returnLocalData()
    }

    private suspend fun updatePlayersWithRemoteApi() {
        val response = remoteApi.getPlayers(cache.getQuery(), cache.nextPlayersIndex())
        if (response is Success) localStorage.updatePlayers(response.items)
    }

    private suspend fun updateTeamsWithTeamsApi() {
        val response = remoteApi.getTeams(cache.getQuery(), cache.nextTeamsIndex())
        if (response is Success) localStorage.updateTeams(response.items)
    }

    private suspend fun returnLocalData() = Result(
        mapper.mapPlayerResponse(localStorage.getPlayers(cache.getQuery())),
        mapper.mapTeamResponse(localStorage.getTeams(cache.getQuery()))
    )
}
