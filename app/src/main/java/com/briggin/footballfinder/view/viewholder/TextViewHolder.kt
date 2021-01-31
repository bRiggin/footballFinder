package com.briggin.footballfinder.view.viewholder

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_holder_text.view.*

class TextViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(@StringRes text: Int) {
        view.loadMoreTextView.text = view.resources.getString(text)
    }
}