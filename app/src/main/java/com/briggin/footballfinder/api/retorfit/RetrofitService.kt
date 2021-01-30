package com.briggin.footballfinder.api.retorfit

import com.briggin.footballfinder.api.retorfit.dto.*
import com.briggin.footballfinder.data.FootballApi
import com.briggin.footballfinder.domain.PlayerDomain
import com.briggin.footballfinder.domain.TeamDomain
import java.lang.Exception

private const val SEARCH_TYPE_PLAYERS = "players"
private const val SEARCH_TYPE_TEAMS = "teams"

class RetrofitService(
    private val restClient: FootballClient,
    private val domainMapper: DomainMapper
) : FootballApi {

    override suspend fun getPlayers(query: String, offset: Long): ApiResponse<PlayerDomain> =
        try {
            val request = FootballRequestBody(query, SEARCH_TYPE_PLAYERS, offset)
            val players = restClient.getData(request).results.players
                ?.mapNotNull { domainMapper.mapPlayer(it) } ?: emptyList()
            Success(players)
        } catch (e: Exception) {
            ApiError()
        }

    override suspend fun getTeams(query: String, offset: Long): ApiResponse<TeamDomain> =
        try {
            val request = FootballRequestBody(query, SEARCH_TYPE_TEAMS, offset)
            val teams = restClient.getData(request).results.teams
                ?.map { domainMapper.mapTeam(it) } ?: emptyList()
            Success(teams)
        } catch (e: Exception) {
            ApiError()
        }
}
