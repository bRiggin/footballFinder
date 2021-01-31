package com.briggin.footballfinder.presentation

import com.briggin.footballfinder.R
import com.briggin.footballfinder.domain.*
import java.lang.StringBuilder

class ModelMapper {

    fun mapToModels(domain: Result): List<FootballModel> {
        val models = domain.playerItems() + domain.teamItems()
        return if (models.isEmpty()) listOf(NoResults()) else models
    }

    private fun Result.playerItems(): List<FootballModel> = when (players) {
        is Complete -> mutableListOf<FootballModel>().apply {
            add(Header(R.string.header_players))
            addAll(players.domains.map { it.toModel() })
        }
        is Partial -> mutableListOf<FootballModel>().apply {
            add(Header(R.string.header_players))
            addAll(players.domains.map { it.toModel() })
            add(LoadMore(ModelType.LoadMorePlayers, R.string.load_more_players))
        }
        is NoDomainsFound -> emptyList()
    }

    private fun PlayerDomain.toModel(): Player = Player(
        id,
        StringBuilder().apply {
            if (firstName.isNotBlank()) append("$firstName ")
            append(secondName)
        }.toString(),
        age.toString(),
        club,
        isFavourite
    )

    private fun Result.teamItems(): List<FootballModel> = when (teams) {
        is Complete -> mutableListOf<FootballModel>().apply {
            add(Header(R.string.header_teams))
            addAll(teams.domains.map { it.toModel() })
        }
        is Partial -> mutableListOf<FootballModel>().apply {
            add(Header(R.string.header_teams))
            addAll(teams.domains.map { it.toModel() })
            add(LoadMore(ModelType.LoadMoreTeams, R.string.load_more_teams))
        }
        is NoDomainsFound -> emptyList()
    }

    private fun TeamDomain.toModel(): Team = Team(name, city, stadium)
}
