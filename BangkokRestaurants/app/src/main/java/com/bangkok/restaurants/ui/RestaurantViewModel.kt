package com.bangkok.restaurants.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bangkok.restaurants.data.Restaurant
import com.bangkok.restaurants.data.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RestaurantRepository(application.applicationContext)

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount

    private var currentQuery = ""

    init {
        loadRestaurants()
    }

    private fun loadRestaurants() {
        viewModelScope.launch {
            _isLoading.value = true
            withContext(Dispatchers.IO) {
                repository.loadAllRestaurants()
            }
            _totalCount.value = repository.getRestaurantCount()
            _restaurants.value = repository.loadAllRestaurants()
            _isLoading.value = false
        }
    }

    fun search(query: String) {
        currentQuery = query
        viewModelScope.launch {
            _isLoading.value = true
            val results = withContext(Dispatchers.IO) {
                repository.searchRestaurants(query)
            }
            _restaurants.value = results
            _isLoading.value = false
        }
    }

    fun clearSearch() {
        currentQuery = ""
        _restaurants.value = repository.loadAllRestaurants()
    }

    fun getCurrentQuery(): String = currentQuery
}
