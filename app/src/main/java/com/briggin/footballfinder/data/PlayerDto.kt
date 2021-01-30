package com.briggin.footballfinder.data

import com.google.gson.annotations.SerializedName

data class PlayerDto(
        @SerializedName("playerID")
        val playerID: String,
        @SerializedName("playerFirstName")
        val playerFirstName: String,
        @SerializedName("playerSecondName")
        val playerSecondName: String,
        @SerializedName("playerNationality")
        val playerNationality: String,
        @SerializedName("playerAge")
        val playerAge: String,
        @SerializedName("playerClub")
        val playerClub: String
)
