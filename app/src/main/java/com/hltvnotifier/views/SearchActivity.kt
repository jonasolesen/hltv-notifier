package com.hltvnotifier.views

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hltvnotifier.R
import com.hltvnotifier.views.adapters.TeamListAdapter
import com.hltvnotifier.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchView: SearchView
    private val teamListAdapter = TeamListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(this)

        teamList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = teamListAdapter
        }

        initializeObservers()
    }

    private fun initializeObservers() {
        viewModel.searchResults.observe(this, Observer { teams ->
            teams.let {
                teamList.visibility = View.VISIBLE
                teamListAdapter.updateTeams(teams)
            }
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                if (isLoading) teamList.visibility = View.INVISIBLE
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        println("Submit")
        if (query != null) viewModel.search(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}