package com.briggin.footballfinder.data

import com.briggin.footballfinder.domain.PlayerDomain
import com.briggin.footballfinder.domain.TeamDomain

interface FootballStorage : FootballApi {

    suspend fun updatePlayers(player: List<PlayerDomain>)

    suspend fun updateTeams(teams: List<TeamDomain>)
}
