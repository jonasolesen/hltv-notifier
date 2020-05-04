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
import com.hltvnotifier.services.EventService
import com.hltvnotifier.services.TeamService
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

        teamsViewModel.teams.observe(this, Observer { teams -> {
            println("Changed")
        } })



    }

    fun addTeam(view: View) {
        println("Hello")
        coroutineScope.launch {
            try {
                //val events = EventService.getFromTeamAsync(astralisId).await().map { println(it.id) }
                val team = teamsViewModel.getFromId(astralisId)
                subscriptionViewModel.subscribe(6665)
                println(team.id)
            } catch (e: Throwable) {
                throw e
            }
        }
    }
}
