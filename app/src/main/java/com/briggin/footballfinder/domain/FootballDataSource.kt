package com.briggin.footballfinder.domain

interface FootballDataSource {
    suspend fun performSearch(query: String): Result
    suspend fun getMorePlayers(): Result
    suspend fun getMoreTeams(): Result

    suspend fun likePlayer(id: String): Result
    suspend fun unlikePlayer(id: String): Result
}
