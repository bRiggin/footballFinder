package com.briggin.footballfinder.common.api.retorfit

import com.briggin.footballfinder.common.api.retorfit.dto.PlayerDto
import com.briggin.footballfinder.common.api.retorfit.dto.TeamDto
import com.briggin.footballfinder.common.domain.PlayerDomain
import com.briggin.footballfinder.common.domain.TeamDomain

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
