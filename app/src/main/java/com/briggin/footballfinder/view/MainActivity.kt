package com.briggin.footballfinder.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.briggin.footballfinder.R

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
