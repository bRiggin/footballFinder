package com.briggin.footballfinder.main.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.briggin.footballfinder.main.presentation.Header
import kotlinx.android.synthetic.main.view_holder_header.view.*

class HeaderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(model: Header) {
        view.headerTextView.text = view.resources.getString(model.title)
    }
}
