package com.briggin.footballfinder.common.data

import com.briggin.footballfinder.common.domain.PlayerDomain
import com.briggin.footballfinder.common.domain.TeamDomain

interface FootballStorage : FootballApi {
    suspend fun updatePlayers(players: List<PlayerDomain>)
    suspend fun updateTeams(teams: List<TeamDomain>)

    suspend fun getFavouritePlayers(): List<PlayerDomain>
    suspend fun likePlayer(id: String)
    suspend fun unlikePlayer(id: String)
}
