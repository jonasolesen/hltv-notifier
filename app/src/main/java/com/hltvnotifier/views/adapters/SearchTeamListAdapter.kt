package com.hltvnotifier.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hltvnotifier.R
import com.hltvnotifier.data.models.SearchResult

class SearchTeamListAdapter(
    var teams: ArrayList<SearchResult.Team>,
    private val onClickListener: ItemClickListener? = null
) : RecyclerView.Adapter<TeamViewHolder>() {

    fun updateTeams(newTeams: List<SearchResult.Team>) {
        teams.clear()
        teams.addAll(newTeams)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TeamViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false),
            onClickListener
        )

    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.adapterPosition
        val team = teams[position]
        holder.bind(team.name!!, team.logoUrl!!)
    }

}