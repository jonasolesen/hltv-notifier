package com.hltvnotifier.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hltvnotifier.R
import com.hltvnotifier.data.models.Subscription
import com.hltvnotifier.data.models.Team
import com.hltvnotifier.loadImage
import com.hltvnotifier.services.NotificationService
import com.hltvnotifier.viewmodels.MatchViewModel
import com.hltvnotifier.viewmodels.SubscriptionViewModel
import com.hltvnotifier.viewmodels.TeamViewModel
import com.hltvnotifier.views.adapters.MatchListAdapter
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamActivity : AppCompatActivity() {
    private var teamId: Int = 0
    private lateinit var team: Team
    private var isSubscribed = false

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var matchViewModel: MatchViewModel
    private lateinit var teamViewModel: TeamViewModel
    private lateinit var subscriptionViewModel: SubscriptionViewModel
    private val matchListAdapter = MatchListAdapter(listOf())
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        matchViewModel = ViewModelProvider(this).get(MatchViewModel::class.java)
        teamViewModel = ViewModelProvider(this).get(TeamViewModel::class.java)
        subscriptionViewModel = ViewModelProvider(this).get(SubscriptionViewModel::class.java)

        matches.apply {
            adapter = matchListAdapter
            layoutManager = LinearLayoutManager(context)
        }

        teamId = intent.getIntExtra("TeamId", 0)

        coroutineScope.launch {
            isSubscribed = subscriptionViewModel.isSubscribed(teamId)
            updateBtn()
            team = teamViewModel.getFromId(teamId)
            teamName.text = team.name
            ranking.text = getString(R.string.team_ranking, team.ranking)
            logoView.loadImage(getString(R.string.team_logo_url, team.id))
            flag.loadImage(team.flagUrl)
        }

        coroutineScope.launch {
            matchListAdapter.update(matchViewModel.getFromTeam(teamId))
        }
    }

    private fun updateBtn() {
        if (isSubscribed) {
            subscribeBtn.text = getString(R.string.unsubscribe)
        } else {
            subscribeBtn.text = getString(R.string.subscribe)
        }
    }

    fun subscribe(view: View) {
        coroutineScope.launch {
            if (!isSubscribed) {
                subscriptionViewModel.subscribe(Subscription(team.id, team.name))

                val matches = matchViewModel.getFromTeam(team.id)
                NotificationService.setNotificationsForMatches(context, matches)
            } else {
                subscriptionViewModel.unsubscribe(team.id)

                val matches = matchViewModel.getFromTeam(team.id)
                NotificationService.cancelNotificationsForMatches(context, matches)
            }

            withContext(Dispatchers.Main) {
                isSubscribed = !isSubscribed
                updateBtn()
            }
        }
    }
}
