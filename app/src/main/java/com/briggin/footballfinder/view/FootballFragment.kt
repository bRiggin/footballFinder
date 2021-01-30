package com.briggin.footballfinder.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.briggin.footballfinder.R
import com.briggin.footballfinder.presentation.FootballViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class FootballFragment : Fragment(R.layout.fragment_football) {

    private val viewModel: FootballViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Default).launch { viewModel.performSearch("alan") }
    }

    companion object {

        fun newInstance() = FootballFragment()
    }
}