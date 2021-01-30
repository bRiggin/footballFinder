package com.briggin.footballfinder.view

import android.app.Application
import com.briggin.footballfinder.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FootballFinderApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FootballFinderApplication)
            modules(koinModule)
        }
    }
}