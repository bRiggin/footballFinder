package com.briggin.footballfinder.api.retorfit.dto

import com.google.gson.annotations.SerializedName

data class FootballDto(
    @SerializedName("result")
    val results: ResultsDto
)

data class ResultsDto(
    @SerializedName("teams")
    val teams: List<TeamDto>?,
    @SerializedName("players")
    val players: List<PlayerDto>?
)
