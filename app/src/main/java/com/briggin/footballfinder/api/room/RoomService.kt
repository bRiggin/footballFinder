package com.briggin.footballfinder.api.room

import com.briggin.footballfinder.api.retorfit.dto.ApiResponse
import com.briggin.footballfinder.api.retorfit.dto.Success
import com.briggin.footballfinder.api.room.player.PlayerDao
import com.briggin.footballfinder.api.room.player.PlayerEntity
import com.briggin.footballfinder.api.room.team.TeamEntity
import com.briggin.footballfinder.api.room.team.TeamsDao
import com.briggin.footballfinder.data.FootballStorage
import com.briggin.footballfinder.domain.PlayerDomain
import com.briggin.footballfinder.domain.TeamDomain

class RoomService(
    private val teamsDao: TeamsDao,
    private val playerDao: PlayerDao
) : FootballStorage {

    override suspend fun updatePlayers(players: List<PlayerDomain>) {
        players.forEach {
            val entity = PlayerEntity.fromDomain(it)
            with(playerDao) {
                getPlayer(it.id)?.let { updatePlayer(entity) } ?: newPlayer(entity)
            }
        }
    }

    override suspend fun updateTeams(teams: List<TeamDomain>) {
        teams.forEach {
            val entity = TeamEntity.fromDomain(it)
            with(teamsDao) {
                getTeam(it.id)?.let { updateTeam(entity) } ?: newTeam(entity)
            }
        }
    }

    override suspend fun getPlayers(query: String, offset: Long): ApiResponse<PlayerDomain> {
        if (query.isBlank()) return Success(emptyList())

        val players = playerDao.getPlayers(query, offset + 10L).map { it.toDomain() }
        return Success(players)
    }

    override suspend fun getTeams(query: String, offset: Long): ApiResponse<TeamDomain> {
        if (query.isBlank()) return Success(emptyList())

        val teams = teamsDao.getTeams(query, offset + 10L).map { it.toDomain() }
        return Success(teams)
    }
}
