package com.example.checkpoint2.activities.googleRepos

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.checkpoint2.adapter.AdapterRepos
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        val reposLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager


        viewModel.initializeRepositoryData()

        adapter = AdapterRepos(this, viewModel.repos.value!!)
        //recyclerView.adapter = adapter
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

        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){

            fun updateScroll()  {
                if (!(viewModel.isUpdating())) {
                viewModel.getNextRepositories()
            }}
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount: Int = reposLayoutManager.itemCount
                val lastVisibleItemIndex: Int = reposLayoutManager.findLastVisibleItemPosition()
                val lastCompletelyVisibleItemPosition: Int = reposLayoutManager.findLastCompletelyVisibleItemPosition()
                if (lastVisibleItemIndex == totalItemCount -1) {
                    updateScroll()
                    //reposLayoutManager.smoothScrollToPosition(recyclerView, null, lastCompletelyVisibleItemPosition)
                }
                //reposLayoutManager.smoothScrollToPosition(recyclerView, null, lastVisibleItemIndex)
                //reposLayoutManager.scrollToPosition(lastVisibleItemIndex)

            }
        })

    }
}
