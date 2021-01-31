package com.briggin.footballfinder.common.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.briggin.footballfinder.R
import com.briggin.footballfinder.main.view.FootballFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, FootballFragment.newInstance())
                .commit()
        }
    }
}
