package com.briggin.footballfinder.common.api.retorfit

import com.briggin.footballfinder.common.api.retorfit.dto.FootballDto
import com.briggin.footballfinder.common.api.retorfit.dto.FootballRequestBody
import retrofit2.http.Body
import retrofit2.http.POST

const val BASE_URL = "http://trials.mtcmobile.co.uk/api/football/1.0/"

interface FootballClient {
    @POST("search")
    suspend fun getData(@Body body: FootballRequestBody): FootballDto
}
