package com.briggin.footballfinder.presentation

import androidx.annotation.StringRes

sealed class FootballModel(val type: ModelType)

data class Header(@StringRes val title: Int) : FootballModel(ModelType.Header)

data class Player(
    val id: String,
    val name: String,
    val age: Int,
    val club: String,
    val isFavourite: Boolean
) : FootballModel(ModelType.Player)

data class Team(
    val name: String,
    val city: Int,
    val stadium: String
) : FootballModel(ModelType.Team)

object Loader : FootballModel(ModelType.Loader)

data class LoadMore(
    private val loadingType: ModelType,
    @StringRes val title: Int
) : FootballModel(loadingType)

enum class ModelType(val id: Int) {
    Header(0),
    Player(1),
    Team(2),
    Loader(3),
    LoadMorePlayers(4),
    LoadMoreTeams(5);

    companion object {
        fun fromID(id: Int) = values().find { id == it.id }
    }
}


