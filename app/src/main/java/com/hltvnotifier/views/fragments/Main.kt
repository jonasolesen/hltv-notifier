package com.hltvnotifier.views.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hltvnotifier.R
import com.hltvnotifier.viewmodels.MatchViewModel
import com.hltvnotifier.viewmodels.SubscriptionViewModel
import com.hltvnotifier.views.TeamActivity
import com.hltvnotifier.views.adapters.ItemClickListener
import com.hltvnotifier.views.adapters.SubscriptionListAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class Main : Fragment(), ItemClickListener {
    private lateinit var subscriptionViewModel: SubscriptionViewModel
    private lateinit var subscriptionListAdapter: SubscriptionListAdapter
    private lateinit var viewModel: SubscriptionViewModel
    private lateinit var matchViewModel: MatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscriptionViewModel = ViewModelProvider(this).get(SubscriptionViewModel::class.java)
        matchViewModel = ViewModelProvider(this).get(MatchViewModel::class.java)
        viewModel = ViewModelProvider(this).get(SubscriptionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        subscriptionListAdapter = SubscriptionListAdapter(context, arrayListOf(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriptions.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = subscriptionListAdapter
        }

        viewModel.subscriptions.observe(
            viewLifecycleOwner,
            Observer { s -> subscriptionListAdapter.updateSubscriptions(s) }
        )
    }

    override fun onClick(view: View, position: Int) {
        val intent = Intent(activity, TeamActivity::class.java).apply {
            putExtra("TeamId", subscriptionListAdapter.subscriptions[position].teamId)
        }
        startActivity(intent)
    }
}