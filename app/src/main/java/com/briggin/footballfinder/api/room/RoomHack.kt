package com.briggin.footballfinder.api.room

import com.briggin.footballfinder.api.retorfit.dto.ApiResponse
import com.briggin.footballfinder.api.retorfit.dto.Success
import com.briggin.footballfinder.data.FootballStorage
import com.briggin.footballfinder.domain.PlayerDomain
import com.briggin.footballfinder.domain.TeamDomain
import java.util.*

class RoomHack : FootballStorage {

    private val persistedPlayers: MutableMap<String, PlayerDomain> = mutableMapOf()
    private val persistedTeams: MutableMap<String, TeamDomain> = mutableMapOf()

    override suspend fun updatePlayers(players: List<PlayerDomain>) {
        players.forEach { domain -> persistedPlayers[domain.id] = domain }
    }

    override suspend fun updateTeams(teams: List<TeamDomain>) {
        teams.forEach { domain -> persistedTeams[domain.id] = domain }
    }

    override suspend fun getPlayers(query: String, offset: Long): ApiResponse<PlayerDomain> {
        val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
        return Success(
            persistedPlayers.values
                .filter { domain ->
                    val firstName = domain.firstName.toLowerCase(Locale.getDefault())
                    val secondName = domain.secondName.toLowerCase(Locale.getDefault())
                    firstName.contains(lowerCaseQuery) || secondName.contains(lowerCaseQuery)
                }
                .take(offset.toInt() + 10)
        )
    }


    override suspend fun getTeams(query: String, offset: Long): ApiResponse<TeamDomain> =
        Success(
            persistedTeams.values
                .filter { it.name.contains(query.toLowerCase(Locale.getDefault())) }
                .take(offset.toInt() + 10)
        )
}
