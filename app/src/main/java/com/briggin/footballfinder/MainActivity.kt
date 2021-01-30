package com.briggin.footballfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.briggin.footballfinder.api.retorfit.DomainMapper
import com.briggin.footballfinder.api.retorfit.FootballClient
import com.briggin.footballfinder.api.retorfit.RetrofitService
import com.briggin.footballfinder.api.room.RoomHack
import com.briggin.footballfinder.data.DataSourceMapper
import com.briggin.footballfinder.data.FootballRepository
import com.briggin.footballfinder.data.QueryCache
import com.briggin.footballfinder.presentation.FootballViewModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val viewModel: FootballViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.Default).launch {
            viewModel.performSearch("alan")
        }
    }
}