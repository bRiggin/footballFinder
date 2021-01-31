package com.briggin.footballfinder.common.data

import com.briggin.footballfinder.common.api.retorfit.dto.ApiError
import com.briggin.footballfinder.common.api.retorfit.dto.Success
import com.briggin.footballfinder.common.domain.*

class FootballRepository(
    private val localStorage: FootballStorage,
    private val remoteApi: FootballApi,
    private val mapper: DataSourceMapper,
    private val cache: QueryCache
) : FootballDataSource, FavoritesDataSource {

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

    override suspend fun unlikeFavouritePlayer(id: String): List<PlayerDomain> {
        localStorage.unlikePlayer(id)
        return getLikedPlayers()
    }

    override suspend fun getLikedPlayers(): List<PlayerDomain> =
        localStorage.getFavouritePlayers()

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
