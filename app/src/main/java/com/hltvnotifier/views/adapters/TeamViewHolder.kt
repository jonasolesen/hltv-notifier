package com.hltvnotifier.views.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hltvnotifier.loadImage
import kotlinx.android.synthetic.main.team_item.view.*

class TeamViewHolder(view: View, private val itemClickListener: ItemClickListener? = null) :
    RecyclerView.ViewHolder(view),
    View.OnClickListener {
    private val name = view.name
    private val logo = view.logoView

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(teamName: String, logoUrl: String) {
        name.text = teamName
        logo.loadImage(logoUrl)
    }

    override fun onClick(view: View) {
        itemClickListener?.onClick(view, adapterPosition)
    }
}