package com.hltvnotifier.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hltvnotifier.R
import com.hltvnotifier.data.models.Match
import kotlinx.android.synthetic.main.match_item.view.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MatchListAdapter(var matches: List<Match>) : RecyclerView.Adapter<MatchListAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MatchViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.match_item, parent, false)
    )

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(matches[position])
    }

    fun update(matches: List<Match>) {
        this.matches = matches
        notifyDataSetChanged()
    }


    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault())
        private val timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME.withZone(ZoneId.systemDefault())
        private val date = view.dateText
        private val time = view.timeText
        private val team1 = view.team1Text
        private val team2 = view.team2Text

        fun bind(match: Match) {
            date.text = dateFormatter.format(match.date)
            time.text = timeFormatter.format(match.date)
            team1.text = match.team1
            team2.text = match.team2
        }
    }


}