package com.hltvnotifier.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hltvnotifier.R
import com.hltvnotifier.loadImage
import com.hltvnotifier.models.SearchResult
import kotlinx.android.synthetic.main.team_item.view.*

class TeamListAdapter(var teams: ArrayList<SearchResult.Team>) : RecyclerView.Adapter<TeamListAdapter.TeamViewHolder>() {
    fun updateTeams(newTeams: List<SearchResult.Team>) {
        teams.clear()
        teams.addAll(newTeams)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TeamViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
    )

    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    class TeamViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val logo = view.logoView
        private val name = view.name
        private val flag = view.flagView

        fun bind(team: SearchResult.Team) {
            logo.loadImage(team.logoUrl)
            name.text = team.name
            flag.loadImage(team.flagUrl)
        }
    }
}