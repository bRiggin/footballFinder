package com.briggin.footballfinder.api.retorfit

import com.briggin.footballfinder.api.retorfit.dto.FootballDto
import com.briggin.footballfinder.api.retorfit.dto.FootballRequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface FootballClient {
    @POST("search")
    suspend fun getData(@Body body: FootballRequestBody): FootballDto
}
