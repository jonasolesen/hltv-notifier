package com.hltvnotifier.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hltvnotifier.R
import com.hltvnotifier.loadImage
import com.hltvnotifier.data.models.SearchResult
import kotlinx.android.synthetic.main.team_item.view.*

class TeamListAdapter(
    var teams: ArrayList<SearchResult.Team>,
    private val onClickListener: ItemClickListener? = null
) : RecyclerView.Adapter<TeamListAdapter.TeamViewHolder>() {

    fun updateTeams(newTeams: List<SearchResult.Team>) {
        teams.clear()
        teams.addAll(newTeams)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TeamViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false),
        onClickListener
    )

    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.adapterPosition
        holder.bind(teams[position])
    }

    class TeamViewHolder(view: View, private val itemClickListener: ItemClickListener? = null) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        private val name = view.name
        private val logo = view.logoView
        private val flag = view.flagView

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(team: SearchResult.Team) {
            name.text = team.name
            logo.loadImage(team.logoUrl)
            flag.loadImage(team.flagUrl)
        }

        override fun onClick(view: View) {
            itemClickListener?.onClick(view, adapterPosition)
        }
    }
}