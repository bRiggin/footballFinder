package com.briggin.footballfinder.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.briggin.footballfinder.presentation.LoadMore
import kotlinx.android.synthetic.main.view_holder_load_more.view.*

class LoadMoreViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(model: LoadMore) {
        view.loadMoreTextView.text = view.resources.getString(model.title)
    }
}