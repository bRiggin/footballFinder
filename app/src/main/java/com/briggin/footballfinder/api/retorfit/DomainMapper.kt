package com.briggin.footballfinder.api.retorfit

import com.briggin.footballfinder.api.retorfit.dto.PlayerDto
import com.briggin.footballfinder.api.retorfit.dto.TeamDto
import com.briggin.footballfinder.domain.PlayerDomain
import com.briggin.footballfinder.domain.TeamDomain

class DomainMapper {

    fun mapPlayer(player: PlayerDto) = player.playerAge.toIntOrNull()?.let {
        PlayerDomain(
            player.playerID,
            player.playerFirstName,
            player.playerSecondName,
            player.playerNationality,
            it,
            player.playerNationality
        )
    }

    fun mapTeam(team: TeamDto) = TeamDomain(
        team.teamID,
        team.teamName,
        team.teamCity,
        team.teamStadium,
        team.teamNationality
    )
}
