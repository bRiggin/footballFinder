package com.briggin.footballfinder.api.retorfit.dto

import com.google.gson.annotations.SerializedName

data class TeamDto(
    @SerializedName("teamID")
    val teamID: String,
    @SerializedName("teamName")
    val teamName: String,
    @SerializedName("teamStadium")
    val teamStadium: String,
    @SerializedName("isNation")
    val isNation: String,
    @SerializedName("teamNationality")
    val teamNationality: String,
    @SerializedName("teamCity")
    val teamCity: String
)
