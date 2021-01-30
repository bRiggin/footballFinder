package com.briggin.footballfinder.data

import com.briggin.footballfinder.api.dto.ApiResponse
import com.briggin.footballfinder.domain.PlayerDomain
import com.briggin.footballfinder.domain.TeamDomain

interface FootballApi {

    suspend fun getPlayers(query: String, offset: Long = 0): ApiResponse<PlayerDomain>

    suspend fun getTeams(query: String, offset: Long = 0): ApiResponse<TeamDomain>
}