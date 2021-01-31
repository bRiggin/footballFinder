package com.briggin.footballfinder.main.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.briggin.footballfinder.R
import com.briggin.footballfinder.favourites.FavouritesFragment
import com.briggin.footballfinder.main.presentation.FootballViewModel
import com.briggin.footballfinder.main.presentation.ModelType
import com.briggin.footballfinder.main.presentation.Player
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
        initialiseRecyclerView()
        initialiseListeners(view)
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

    private fun initialiseRecyclerView() {
        footballRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FootballFragment.adapter
        }
    }

    private fun initialiseListeners(view: View) {
        with(viewModel) {
            state.observe(viewLifecycleOwner, { adapter.update(it) })
            error.observe(viewLifecycleOwner, {
                Snackbar.make(view, resources.getString(it), Snackbar.LENGTH_LONG).show()
            })
            footballEditText.doAfterTextChanged {
                CoroutineScope(Dispatchers.Default).launch { newSearch(it.toString()) }
            }
        }
        favouritesButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer, FavouritesFragment.newInstance())
                ?.addToBackStack("FavouritesFragment")
                ?.commit()
        }
    }

    companion object {

        fun newInstance() = FootballFragment()
    }
}