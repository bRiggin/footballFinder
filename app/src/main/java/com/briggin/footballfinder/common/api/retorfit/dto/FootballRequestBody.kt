package com.briggin.footballfinder.common.api.retorfit.dto

import com.google.gson.annotations.SerializedName

data class FootballRequestBody(
    @SerializedName("searchString")
    val query: String,
    @SerializedName("searchType")
    val type: String,
    @SerializedName("offset")
    val offset: Long
)
