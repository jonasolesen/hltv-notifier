package com.hltvnotifier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hltvnotifier.services.HltvService
//import com.hltvnotifier.services.EventService
//import com.hltvnotifier.services.MatchService
//import com.hltvnotifier.services.TeamService
import com.hltvnotifier.viewmodels.SubscriptionViewModel
import com.hltvnotifier.viewmodels.TeamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TeamAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var teamsViewModel: TeamViewModel
    private lateinit var subscriptionViewModel: SubscriptionViewModel
    private val hltvService = HltvService.getService()
    private val astralisId: Int = 6665
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teamsViewModel = ViewModelProvider(this).get(TeamViewModel::class.java)
        subscriptionViewModel = ViewModelProvider(this).get(SubscriptionViewModel::class.java)

        viewManager = LinearLayoutManager(this)
        setContentView(R.layout.activity_main_list)
        println("Hello!")

        viewAdapter = TeamAdapter(teamsViewModel, subscriptionViewModel)

        recyclerView = findViewById<RecyclerView>(R.id.teamList).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        subscriptionViewModel.subscriptions.observe(this, Observer { teams ->
            viewAdapter.setDate(teams)
        })
    }

    fun addTeam(view: View) {
        println("Hello")
        coroutineScope.launch {
            try {
//                val team = teamsViewModel.getFromId(astralisId)
                subscriptionViewModel.subscribe(6665)

                // TODO Save them to db and create notifications for the matches.
//                val matches = MatchService.getFromTeamAsync(6665).await()
//                for (match in matches) {
//                    println(match.Id.toString() + " " + match.date.toString() + " " + match.team1 + " " + match.team2)
//                }
            } catch (e: Throwable) {
                throw e
            }
        }
    }
}
