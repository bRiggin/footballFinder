package com.briggin.footballfinder

import com.briggin.footballfinder.api.retorfit.DomainMapper
import com.briggin.footballfinder.api.retorfit.FootballClient
import com.briggin.footballfinder.api.retorfit.RetrofitService
import com.briggin.footballfinder.api.room.RoomHack
import com.briggin.footballfinder.data.*
import com.briggin.footballfinder.domain.FootballDataSource
import com.briggin.footballfinder.presentation.FootballViewModel
import com.briggin.footballfinder.presentation.ModelMapper
import com.google.gson.GsonBuilder
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val koinModule = module {

    viewModel { FootballViewModel(get(), get()) }

    factory { ModelMapper() }

    factory { FootballRepository(get(), get(), get(), get()) }
        .bind(FootballDataSource::class)

    factory { QueryCache() }

    factory { DataSourceMapper() }

    factory { RoomHack() }.bind(FootballStorage::class)

    factory { RetrofitService(get(), get()) }.bind(FootballApi::class)

    factory { DomainMapper() }

    factory {
        Retrofit.Builder()
            .baseUrl("http://trials.mtcmobile.co.uk/api/football/1.0/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(FootballClient::class.java)
    }.bind(FootballClient::class)
}