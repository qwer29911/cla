package com.bangkok.restaurants.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkok.restaurants.R
import com.bangkok.restaurants.data.Restaurant
import com.bangkok.restaurants.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RestaurantViewModel by viewModels()
    private lateinit var adapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearch()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = RestaurantAdapter { restaurant ->
            openRestaurantDetail(restaurant)
        }

        binding.restaurantsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString() ?: ""
                binding.clearButton.isVisible = query.isNotEmpty()
                viewModel.search(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(binding.searchEditText.text.toString())
                true
            } else {
                false
            }
        }

        binding.clearButton.setOnClickListener {
            binding.searchEditText.text?.clear()
            viewModel.clearSearch()
        }
    }

    private fun observeViewModel() {
        viewModel.restaurants.observe(this) { restaurants ->
            adapter.submitList(restaurants)
            updateResultCount(restaurants.size)
            updateEmptyState(restaurants.isEmpty())
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.totalCount.observe(this) { totalCount ->
            if (viewModel.getCurrentQuery().isEmpty()) {
                binding.resultCountText.text = getString(R.string.all_restaurants, totalCount)
            }
        }
    }

    private fun updateResultCount(count: Int) {
        val query = viewModel.getCurrentQuery()
        binding.resultCountText.text = if (query.isEmpty()) {
            getString(R.string.all_restaurants, count)
        } else {
            when (count) {
                1 -> getString(R.string.results_count_single)
                else -> getString(R.string.results_count, count)
            }
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        val hasQuery = viewModel.getCurrentQuery().isNotEmpty()
        binding.emptyView.isVisible = isEmpty && hasQuery
        binding.restaurantsRecyclerView.isVisible = !isEmpty
    }

    private fun openRestaurantDetail(restaurant: Restaurant) {
        val intent = Intent(this, RestaurantDetailActivity::class.java).apply {
            putExtra(RestaurantDetailActivity.EXTRA_RESTAURANT, restaurant)
        }
        startActivity(intent)
    }
}
