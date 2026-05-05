package com.example.catapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapp.adapter.CatAdapter
import com.example.catapp.databinding.ActivityMainBinding
import com.example.catapp.viewmodel.CatViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CatViewModel
    private lateinit var catAdapter: CatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CatViewModel::class.java]
        catAdapter = CatAdapter()

        setupRecyclerView()
        setupSwipeRefresh()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = catAdapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadImages()
        }
    }

    private fun observeViewModel() {
        viewModel.images.observe(this) { images ->
            catAdapter.submitList(images)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            // Stop the swipe refresh animation when loading is complete
            if (!isLoading) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            
            // Show the central progress bar only on initial load (not on swipe refresh)
            if (isLoading && !binding.swipeRefreshLayout.isRefreshing) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}
