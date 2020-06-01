package com.hltvnotifier.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hltvnotifier.R
import com.hltvnotifier.data.models.Subscription

class SubscriptionListAdapter(
    private val context: Context,
    var subscriptions: ArrayList<Subscription>,
    private val onClickListener: ItemClickListener? = null
) : RecyclerView.Adapter<TeamViewHolder>() {

    fun updateSubscriptions(newSubscriptions: List<Subscription>) {
        subscriptions.clear()
        subscriptions.addAll(newSubscriptions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TeamViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false),
            onClickListener
        )

    override fun getItemCount() = subscriptions.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.adapterPosition
        val subscription = subscriptions[position]
        holder.bind(subscription.teamName, context.getString(R.string.team_logo_url, subscription.teamId))
    }

}