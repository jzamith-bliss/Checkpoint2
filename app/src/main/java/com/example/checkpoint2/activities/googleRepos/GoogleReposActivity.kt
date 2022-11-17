package com.example.checkpoint2.activities.googleRepos

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.checkpoint2.adapter.AdapterRepos
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.checkpoint2.databinding.ActivityGoogleReposBinding

class GoogleReposActivity : AppCompatActivity() {

    private val viewModel: GoogleReposViewModel by lazy {
        val activity = requireNotNull(this) {}
        ViewModelProvider(this, GoogleReposViewModel.Factory(this.application))[GoogleReposViewModel::class.java]
    }
    private lateinit var binding: ActivityGoogleReposBinding
    private lateinit var adapter: AdapterRepos

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleReposBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.recyclerView
        binding.viewModel = viewModel

        viewModel.initializeRepositoryData()

        adapter = AdapterRepos(this, viewModel.repos.value!!)
        recyclerView.adapter = adapter
        //val repositoryAdapter = binding.recyclerView.adapter as AdapterRepos

        viewModel.repos.observe(this) {
            updatedRepos -> binding.recyclerView.adapter = AdapterRepos(this, dataset = updatedRepos)
            if (updatedRepos.isNotEmpty()) {
                adapter.notifyItemRangeInserted(
                    viewModel.getNextRepositoryPosition(),
                    viewModel.getRepositoriesUpdateSize()
                )
            }
            viewModel.finishUpdate()
        }

        binding.recyclerView.layoutManager?.let {
            binding.recyclerView.addOnScrollListener(object: EndlessRecyclerOnScrollListener(it as LinearLayoutManager){
                override fun onLoadMore(lastVisibleIndex: Int) {
                    if (!(viewModel.isUpdating())) { viewModel.getNextRepositories() }
                }
            })
        }
    }
}