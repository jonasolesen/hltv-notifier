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
import com.hltvnotifier.viewmodels.SubscriptionViewModel
import com.hltvnotifier.views.adapters.ItemClickListener
import com.hltvnotifier.views.adapters.SubscriptionListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var subscriptionViewModel: SubscriptionViewModel

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val subscriptionListAdapter = SubscriptionListAdapter(this, arrayListOf(), this)
    private lateinit var viewModel: SubscriptionViewModel



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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscriptionViewModel = ViewModelProvider(this).get(SubscriptionViewModel::class.java)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(SubscriptionViewModel::class.java)

        subscriptions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = subscriptionListAdapter
        }

        viewModel.subscriptions.observe(this, Observer { s -> subscriptionListAdapter.updateSubscriptions(s) })

    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun onClick(view: View, position: Int) {
        val intent = Intent(this, TeamActivity::class.java).apply {
            putExtra("TeamId", subscriptionListAdapter.subscriptions[position].teamId)
        }
        startActivity(intent)
    }
}
