package com.briggin.footballfinder.common.api.retorfit

import com.briggin.footballfinder.common.api.retorfit.dto.ApiError
import com.briggin.footballfinder.common.api.retorfit.dto.ApiResponse
import com.briggin.footballfinder.common.api.retorfit.dto.FootballRequestBody
import com.briggin.footballfinder.common.api.retorfit.dto.Success
import com.briggin.footballfinder.common.data.FootballApi
import com.briggin.footballfinder.common.domain.PlayerDomain
import com.briggin.footballfinder.common.domain.TeamDomain

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
