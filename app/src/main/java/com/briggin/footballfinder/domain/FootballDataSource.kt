package com.briggin.footballfinder.domain

interface FootballDataSource {
    suspend fun performSearch(query: String): Result
    suspend fun getMorePlayers(): Result
    suspend fun getMoreTeams(): Result
}
