package com.briggin.footballfinder.data

import com.briggin.footballfinder.domain.FootballDataSource
import com.briggin.footballfinder.domain.Result

class FootballRepository(
    localApi: FootballApi,
    remoteApi: FootballApi
) : FootballDataSource {

    override suspend fun performSearch(query: String): Result {
        TODO("Not yet implemented")
    }

    override suspend fun getMorePlayers(): Result {
        TODO("Not yet implemented")
    }

    override suspend fun getMoreTeams(): Result {
        TODO("Not yet implemented")
    }
}
