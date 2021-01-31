package com.briggin.footballfinder.main.presentation

import com.briggin.footballfinder.R
import com.briggin.footballfinder.common.FlagRes
import com.briggin.footballfinder.common.domain.*

class ModelMapper {

    fun mapToModels(domain: Result): List<FootballModel> {
        val models = domain.playerItems() + domain.teamItems()
        return if (models.isEmpty()) listOf(NoResults()) else models
    }

    private fun Result.playerItems(): List<FootballModel> = when (players) {
        is Complete -> mutableListOf<FootballModel>().apply {
            add(Header(R.string.header_players))
            addAll(players.domains.map { playerToModel(it) })
        }
        is Partial -> mutableListOf<FootballModel>().apply {
            add(Header(R.string.header_players))
            addAll(players.domains.map { playerToModel(it) })
            add(LoadMore(ModelType.LoadMorePlayers, R.string.load_more_players))
        }
        is NoDomainsFound -> emptyList()
    }

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

    private fun TeamDomain.toModel(): Team = Team(
        name,
        city,
        stadium,
        FlagRes.fromKey(nationality).resID
    )

    companion object {
        fun playerToModel(domain: PlayerDomain): Player = Player(
            domain.id,
            StringBuilder().apply {
                if (domain.firstName.isNotBlank()) append("${domain.firstName} ")
                append(domain.secondName)
            }.toString(),
            domain.age?.toString(),
            domain.club,
            domain.isFavourite,
            FlagRes.fromKey(domain.nationality).resID
        )
    }
}
