package com.briggin.footballfinder.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.briggin.footballfinder.R
import com.briggin.footballfinder.presentation.Team
import kotlinx.android.synthetic.main.view_holder_team.view.*

class TeamViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(model: Team) {
        with(view) {
            teamName.text = model.name
            teamCity.text = resources.getString(R.string.club_city, model.city)
            teamStadium.text = resources.getString(R.string.club_stadium, model.stadium)
            teamImage.setImageResource(model.drawableResID)
        }
    }
}