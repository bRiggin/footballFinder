package com.briggin.footballfinder.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.briggin.footballfinder.R
import com.briggin.footballfinder.main.presentation.*
import com.briggin.footballfinder.main.view.viewholder.*

class FootballAdapter(
    private val listener: Listener
) : ListAdapter<FootballModel, RecyclerView.ViewHolder>(FootballAdapterDiffCallback()) {

    fun update(models: List<FootballModel>) {
        submitList(models)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ModelType.fromID(viewType)) {
            ModelType.Header ->
                HeaderViewHolder(inflater.inflate(R.layout.view_holder_header, parent, false))
            ModelType.Player ->
                PlayerViewHolder(inflater.inflate(R.layout.view_holder_player, parent, false), listener)
            ModelType.Team ->
                TeamViewHolder(inflater.inflate(R.layout.view_holder_team, parent, false))
            ModelType.Loader ->
                LoaderViewHolder(inflater.inflate(R.layout.view_holder_loader, parent, false))
            ModelType.LoadMorePlayers, ModelType.LoadMoreTeams, ModelType.NoResultsFound ->
                TextViewHolder(inflater.inflate(R.layout.view_holder_text, parent, false))
            null -> throw IllegalArgumentException("FootballAdapter can't create ViewHolder for unknown view type")
        }
    }

    override fun getItemViewType(position: Int): Int =
        currentList[position].type.id

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val model = currentList[position] as FootballModel) {
            is Header -> (holder as? HeaderViewHolder)?.bindView(model)
            is Player -> (holder as? PlayerViewHolder)?.bindView(model)
            is Team -> (holder as? TeamViewHolder)?.bindView(model)
            is NoResults -> (holder as? TextViewHolder)?.bindView(model.title)
            is LoadMore -> {
                (holder as? TextViewHolder)?.bindView(model.title)
                holder.itemView.setOnClickListener { listener.onLoadMoreItems(model.type) }
            }
        }
    }

    interface Listener : LikeActionListener {
        fun onLoadMoreItems(type: ModelType)
    }

    interface LikeActionListener {
        fun onLikeAction(player: Player)
    }
}

private class FootballAdapterDiffCallback : DiffUtil.ItemCallback<FootballModel>() {

    override fun areItemsTheSame(oldItem: FootballModel, newItem: FootballModel): Boolean =
        oldItem.isSameItem(newItem)

    override fun areContentsTheSame(oldItem: FootballModel, newItem: FootballModel): Boolean =
        oldItem == newItem
}