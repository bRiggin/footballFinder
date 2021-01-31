package com.briggin.footballfinder.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.briggin.footballfinder.R
import com.briggin.footballfinder.presentation.FootballViewModel
import com.briggin.footballfinder.presentation.ModelType
import com.briggin.footballfinder.presentation.Player
import com.google.android.material.snackbar.Snackbar
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

        viewModel.state.observe(viewLifecycleOwner, { adapter.update(it) })

        viewModel.error.observe(viewLifecycleOwner, {
            Snackbar.make(view, resources.getString(it), Snackbar.LENGTH_LONG).show()
        })

        footballEditText.doAfterTextChanged {
            CoroutineScope(Dispatchers.Default).launch { viewModel.newSearch(it.toString()) }
        }
    }

    override fun onLoadMoreItems(type: ModelType) {
        CoroutineScope(Dispatchers.Default).launch {
            when (type) {
                ModelType.LoadMorePlayers -> viewModel.loadMorePlayers()
                ModelType.LoadMoreTeams -> viewModel.loadMoreTeams()
            }
        }
    }

    override fun onLikeAction(player: Player) {
        CoroutineScope(Dispatchers.Default).launch { viewModel.onLikeAction(player) }
    }

    companion object {

        fun newInstance() = FootballFragment()
    }
}