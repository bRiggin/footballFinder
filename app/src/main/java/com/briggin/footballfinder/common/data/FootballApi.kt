package com.briggin.footballfinder.common.data

import com.briggin.footballfinder.common.api.retorfit.dto.ApiResponse
import com.briggin.footballfinder.common.domain.PlayerDomain
import com.briggin.footballfinder.common.domain.TeamDomain

interface FootballApi {

    suspend fun getPlayers(query: String, offset: Long = 0): ApiResponse<PlayerDomain>

    suspend fun getTeams(query: String, offset: Long = 0): ApiResponse<TeamDomain>
}