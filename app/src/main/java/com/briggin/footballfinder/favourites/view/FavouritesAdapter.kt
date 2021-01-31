package com.briggin.footballfinder.favourites.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.briggin.footballfinder.R
import com.briggin.footballfinder.main.presentation.*
import com.briggin.footballfinder.main.view.FootballAdapter
import com.briggin.footballfinder.main.view.viewholder.*

class FavouritesAdapter(
    private val listener: FootballAdapter.LikeActionListener
) : ListAdapter<Player, PlayerViewHolder>(FavouritesAdapterDiffCallback()) {

    fun update(players: List<Player>){
        submitList(players)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlayerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_player, parent, false),
            listener
        )

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindView(currentList[position])
    }
}

private class FavouritesAdapterDiffCallback : DiffUtil.ItemCallback<Player>() {

    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean =
        oldItem == newItem
}
