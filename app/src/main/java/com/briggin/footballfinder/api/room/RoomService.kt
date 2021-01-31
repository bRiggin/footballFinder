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
                getPlayer(it.id)?.let { existing ->
                    updatePlayer(entity.copy(isFavourite = existing.isFavourite))
                } ?: newPlayer(entity)
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

        val domains = playerDao.getPlayers()
            .asSequence()
            .filter { it.firstName.contains(query, true) || it.secondName.contains(query, true) }
            .take((offset + 10L).toInt())
            .map { it.toDomain() }
            .toList()

        return Success(domains)
    }

    override suspend fun getTeams(query: String, offset: Long): ApiResponse<TeamDomain> {
        if (query.isBlank()) return Success(emptyList())

        val teams = teamsDao.getTeams()
            .asSequence()
            .filter { it.name.contains(query, true) }
            .take((offset + 10L).toInt())
            .map { it.toDomain() }
            .toList()

        return Success(teams)
    }

    override suspend fun getFavouritePlayers(): List<PlayerDomain> =
        playerDao.getFavouritePlayers().map { it.toDomain() }

    override suspend fun likePlayer(id: String) { performLikeAction(id, true) }

    override suspend fun unlikePlayer(id: String) { performLikeAction(id, false) }

    private suspend fun performLikeAction(id: String, isLiked: Boolean) {
        playerDao.getPlayer(id)?.let { playerDao.updatePlayer(it.copy(isFavourite = isLiked)) }
    }
}
