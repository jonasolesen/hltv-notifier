package com.hltvnotifier

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hltvnotifier.data.entities.SubscriptionEntity
import com.hltvnotifier.viewmodels.SubscriptionViewModel
import com.hltvnotifier.viewmodels.TeamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamAdapter(val teamViewModel: TeamViewModel, val subscriptionViewModel: SubscriptionViewModel) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {
    private var teams: List<SubscriptionEntity> = listOf()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    class TeamViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.team_row, parent, false) as TextView
        val holder = TeamViewHolder(textView)
        textView.setOnClickListener { unsubscribe(holder.adapterPosition) }
        return holder
    }

    private fun unsubscribe(position: Int) {
        coroutineScope.launch { subscriptionViewModel.unsubscribe(teams[position].teamId) }
    }

    fun setDate(teams: List<SubscriptionEntity>) {
        this.teams = teams
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        coroutineScope.launch {
            val name = teamViewModel.getFromId(teams[position].teamId).name
            holder.textView.text = name
        }

    }

}