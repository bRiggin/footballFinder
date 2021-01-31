package com.briggin.footballfinder

import com.briggin.footballfinder.api.retorfit.BASE_URL
import com.briggin.footballfinder.api.retorfit.DomainMapper
import com.briggin.footballfinder.api.retorfit.FootballClient
import com.briggin.footballfinder.api.retorfit.RetrofitService
import com.briggin.footballfinder.api.room.FootballDatabase
import com.briggin.footballfinder.api.room.RoomService
import com.briggin.footballfinder.data.*
import com.briggin.footballfinder.domain.FootballDataSource
import com.briggin.footballfinder.main.presentation.FootballViewModel
import com.briggin.footballfinder.main.presentation.ModelMapper
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
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

    factory { RoomService(get(), get()) }.bind(FootballStorage::class)

    factory { FootballDatabase.instance(androidApplication()).teamsDao() }

    factory { FootballDatabase.instance(androidApplication()).playersDao() }

    factory { RetrofitService(get(), get()) }.bind(FootballApi::class)

    factory { DomainMapper() }

    factory {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(FootballClient::class.java)
    }.bind(FootballClient::class)
}