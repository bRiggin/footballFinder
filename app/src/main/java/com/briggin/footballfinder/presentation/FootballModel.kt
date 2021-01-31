package com.briggin.footballfinder.presentation

import androidx.annotation.StringRes
import com.briggin.footballfinder.R

sealed class FootballModel(
    val type: ModelType,
    val isSameItem: (FootballModel) -> Boolean
)

data class Header(
    @StringRes val title: Int
) : FootballModel(
    ModelType.Header,
    { other -> other is Header && other.title == title }
)

data class Player(
    val id: String,
    val name: String,
    val age: String,
    val club: String,
    val isFavourite: Boolean
) : FootballModel(
    ModelType.Player,
    { other -> other is Player && other.id == id }
)

data class Team(
    val name: String,
    val city: String,
    val stadium: String
) : FootballModel(
    ModelType.Team,
    { other -> other is Team && other.name == name }
)

object Loading : FootballModel(ModelType.Loader, { other -> other is Loading })

data class LoadMore(
    private val loadingType: ModelType,
    @StringRes val title: Int
) : FootballModel(
    loadingType,
    { other -> other is LoadMore && other.loadingType == loadingType }
)

data class NoResults(
    @StringRes val title: Int = R.string.no_results_found
) : FootballModel(ModelType.NoResultsFound, { other -> other is NoResults })

enum class ModelType(val id: Int) {
    Header(0),
    Player(1),
    Team(2),
    Loader(3),
    LoadMorePlayers(4),
    LoadMoreTeams(5),
    NoResultsFound(6);

    companion object {
        fun fromID(id: Int) = values().find { id == it.id }
    }
}


