package com.briggin.footballfinder.api.retorfit

import com.briggin.footballfinder.api.retorfit.dto.PlayerDto
import com.briggin.footballfinder.api.retorfit.dto.TeamDto
import com.briggin.footballfinder.domain.PlayerDomain
import com.briggin.footballfinder.domain.TeamDomain

class DomainMapper {

    fun mapPlayer(player: PlayerDto) = PlayerDomain(
        player.playerID,
        player.playerFirstName,
        player.playerSecondName,
        player.playerNationality,
        player.playerAge.toIntOrNull(),
        player.playerClub
    )

    fun mapTeam(team: TeamDto) = TeamDomain(
        team.teamID,
        team.teamName,
        team.teamCity,
        team.teamStadium,
        team.teamNationality
    )
}
