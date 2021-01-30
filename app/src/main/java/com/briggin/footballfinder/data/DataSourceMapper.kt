package com.briggin.footballfinder.data

import com.briggin.footballfinder.api.dto.ApiResponse
import com.briggin.footballfinder.domain.FootballDomain
import com.briggin.footballfinder.domain.PlayerDomain
import com.briggin.footballfinder.domain.TeamDomain

class DataSourceMapper {

    fun mapPlayerResponse(response: ApiResponse<PlayerDomain>): FootballDomain<PlayerDomain> {
        TODO()
    }

    fun mapTeamResponse(response: ApiResponse<TeamDomain>): FootballDomain<TeamDomain> {
        TODO()
    }
}
