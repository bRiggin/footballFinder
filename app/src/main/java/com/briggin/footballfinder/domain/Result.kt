package com.briggin.footballfinder.domain

data class Result(
    val players: FootballDomain<PlayerDomain>,
    val teams: FootballDomain<PlayerDomain>
)
