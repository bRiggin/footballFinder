package com.briggin.footballfinder.common.domain

data class Result(
    val players: FootballDomain<PlayerDomain>,
    val teams: FootballDomain<TeamDomain>,
    val remoteErrors: Set<ResultError>
)

enum class ResultError {
    Players, Teams
}
