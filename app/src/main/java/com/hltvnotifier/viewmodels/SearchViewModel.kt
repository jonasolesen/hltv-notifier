package com.hltvnotifier.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hltvnotifier.models.SearchResult
import com.hltvnotifier.services.SearchService
import kotlinx.coroutines.*

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private var job: Job? = null
    private val searchService = SearchService.getSearchService()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError("Exception: ${throwable.localizedMessage}")
    }

    val searchResults = MutableLiveData<List<SearchResult.Team>>()
    val searchError = MutableLiveData<String?>()
    val isLoading = MutableLiveData(false)


    fun search(input: String) {
        if (input.length < 2) return
        isLoading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = searchService.findTeam(input)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    searchResults.value = response.body()
                    isLoading.value = false
                } else {
                    handleError(response.message())
                }
            }
        }
    }

    private fun handleError(message: String) {
        searchError.value = message
        isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}