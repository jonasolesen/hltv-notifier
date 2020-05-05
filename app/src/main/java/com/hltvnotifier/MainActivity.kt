package com.hltvnotifier

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hltvnotifier.viewmodels.SubscriptionViewModel
import com.hltvnotifier.viewmodels.TeamViewModel
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var subscriptionViewModel: SubscriptionViewModel
    private lateinit var teamViewModel: TeamViewModel
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var isLoading = false
        set(value) {
            field = value
            if (value) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.INVISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        subscriptionViewModel = ViewModelProvider(this).get(SubscriptionViewModel::class.java)
        teamViewModel = ViewModelProvider(this).get(TeamViewModel::class.java)

        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
//        subscriptionViewModel.subscriptions.observe(this, Observer { subscriptions ->
//            subscriptions.forEach { println(it.teamId) }
//        })
        teamViewModel.teams.observe(this, Observer { teams ->
            teams.forEach { println("Something updated: ${it.id}") }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    fun scrape(view: View) {
        val astralisId = 6665
        coroutineScope.launch {
            try {
                isLoading = true
//                val team = TeamService.getTeamAsync(astralisId).await().run { println(ranking) }
//                val events = EventService.getFromTeamAsync(astralisId).await().map { println(it.id) }
                val team = teamViewModel.getFromId(astralisId)
                println(team.id)
            } catch (e: Throwable) {
                throw e
            } finally {
                isLoading = false
            }
        }
    }

    fun subscribe(view: View) {
        val astralisId = 6665
        coroutineScope.launch {
            subscriptionViewModel.subscribe(astralisId)
            println(subscriptionViewModel.subscriptions.value)
        }
        println("Subscribe")
    }

    fun unsubscribe(view: View) {
        val astralisId = 6665
        coroutineScope.launch {
            subscriptionViewModel.unsubscribe(astralisId)
            println(subscriptionViewModel.subscriptions.value)
        }
        println("Unsubscribe")
    }

    fun search(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}
