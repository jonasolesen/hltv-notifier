package com.hltvnotifier.views

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hltvnotifier.R
import com.hltvnotifier.views.adapters.SearchTeamListAdapter
import com.hltvnotifier.viewmodels.SearchViewModel
import com.hltvnotifier.views.adapters.ItemClickListener
import kotlinx.android.synthetic.main.activity_search.teamList

class SearchActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var viewModel: SearchViewModel
    private val teamListAdapter = SearchTeamListAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        teamList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = teamListAdapter
        }

        initializeObservers()
        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false
            isFocusedByDefault = true
        }

        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onClick(view: View, position: Int) {
        val intent = Intent(this, TeamActivity::class.java).apply {
            putExtra("TeamId", teamListAdapter.teams[position].id)
        }
        startActivity(intent)
    }

    private fun handleIntent(intent: Intent) {
        println("Called")
        intent.getStringExtra(SearchManager.QUERY)?.also { query ->
            viewModel.search(query)
        }
    }

    private fun initializeObservers() {
        viewModel.searchResults.observe(this, Observer { teams ->
            teams.let {
                teamList.visibility = View.VISIBLE
                teamListAdapter.updateTeams(teams)
            }
        })
    }
}
