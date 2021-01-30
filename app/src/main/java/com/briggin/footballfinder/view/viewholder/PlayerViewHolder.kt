package com.briggin.footballfinder.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.briggin.footballfinder.presentation.Player
import kotlinx.android.synthetic.main.view_holder_player.view.*

class PlayerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(model: Player) {
        with(view) {
            playerName.text = model.name
            playerAge.text = model.age.toString()
            playerClub.text = model.club
        }
    }
}