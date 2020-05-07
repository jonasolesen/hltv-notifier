package com.hltvnotifier.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hltvnotifier.R
import com.hltvnotifier.data.models.Team
import com.hltvnotifier.loadImage
import com.hltvnotifier.services.HltvService
import com.hltvnotifier.viewmodels.MatchViewModel
import com.hltvnotifier.viewmodels.TeamViewModel
import com.hltvnotifier.views.adapters.MatchListAdapter
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.activity_team.logoView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamActivity : AppCompatActivity() {
    private var teamId: Int = 0
    private lateinit var team: Team

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val hltvService = HltvService.getService()
    private lateinit var matchViewModel: MatchViewModel
    private lateinit var teamViewModel: TeamViewModel
    private val matchListAdapter = MatchListAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        matchViewModel = ViewModelProvider(this).get(MatchViewModel::class.java)
        teamViewModel = ViewModelProvider(this).get(TeamViewModel::class.java)

        matches.apply {
            adapter = matchListAdapter
            layoutManager = LinearLayoutManager(context)
        }

        teamId = intent.getIntExtra("TeamId", 0)

        coroutineScope.launch {
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
}
