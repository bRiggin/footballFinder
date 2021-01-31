package com.briggin.footballfinder.favourites.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.briggin.footballfinder.R
import com.briggin.footballfinder.favourites.presentation.FavouritesViewModel
import com.briggin.footballfinder.main.presentation.Player
import com.briggin.footballfinder.main.view.FootballAdapter
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.android.synthetic.main.fragment_football.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment(R.layout.fragment_favourites),
    FootballAdapter.LikeActionListener {

    private val adapter: FavouritesAdapter = FavouritesAdapter(this)
    private val viewModel: FavouritesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseRecyclerView()
        viewModel.state.observe(viewLifecycleOwner, { adapter.update(it) })
    }

    override fun onLikeAction(player: Player) {
        CoroutineScope(Dispatchers.Default).launch { viewModel.unlikePlayer(player) }
    }

    private fun initialiseRecyclerView() {
        favouritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavouritesFragment.adapter
        }
    }

    companion object {
        fun newInstance() = FavouritesFragment()
    }
}
