package com.briggin.footballfinder.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.briggin.footballfinder.R
import com.briggin.footballfinder.presentation.FootballViewModel
import com.briggin.footballfinder.presentation.ModelType
import kotlinx.android.synthetic.main.fragment_football.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class FootballFragment : Fragment(R.layout.fragment_football), FootballAdapter.Listener {

    private val viewModel: FootballViewModel by viewModel()
    private val adapter: FootballAdapter = FootballAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        footballRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FootballFragment.adapter
        }

        viewModel.state.observe(viewLifecycleOwner, {
            adapter.update(it)
        })

        CoroutineScope(Dispatchers.Default).launch { viewModel.performSearch("alan") }
    }

    override fun loadMoreItems(type: ModelType) {
        CoroutineScope(Dispatchers.Default).launch {
            when (type) {
                ModelType.LoadMorePlayers -> viewModel.loadMorePlayers()
                ModelType.LoadMoreTeams -> viewModel.loadMoreTeams()
            }
        }
    }

    companion object {

        fun newInstance() = FootballFragment()
    }
}