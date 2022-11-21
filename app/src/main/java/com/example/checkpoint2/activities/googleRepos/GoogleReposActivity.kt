package com.example.checkpoint2.activities.googleRepos

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.checkpoint2.adapter.AdapterRepos
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checkpoint2.R
import com.example.checkpoint2.databinding.ActivityGoogleReposBinding

class GoogleReposActivity : AppCompatActivity() {

    private val viewModel: GoogleReposViewModel by lazy {
        ViewModelProvider(this, GoogleReposViewModel.Factory(this.application))[GoogleReposViewModel::class.java]
    }
    private lateinit var binding: ActivityGoogleReposBinding
    private lateinit var adapter: AdapterRepos

    @SuppressLint("NotifyDataSetChanged", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Checkpoint2)
        binding = ActivityGoogleReposBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.recyclerView
        binding.viewModel = viewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        val reposLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)

        viewModel.initializeRepositoryData()

        adapter = AdapterRepos(this, viewModel.repos.value!!)
        recyclerView.adapter = adapter
        val progressBar = binding.progressBar
        //val repositoryAdapter = binding.recyclerView.adapter as AdapterRepos

        viewModel.repos.observe(this) {
            updatedRepos -> adapter.dataset = updatedRepos
            if (updatedRepos.isNotEmpty()) {
                adapter.notifyItemRangeInserted(
                    viewModel.getNextRepositoryPosition(),
                    viewModel.getRepositoriesUpdateSize()
                )

            }
            progressBar.visibility = View.INVISIBLE
            viewModel.finishUpdate()
        }

        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            fun updateScroll()  {
                if (!(viewModel.isUpdating())) {
                    progressBar.visibility = View.VISIBLE
                    viewModel.getNextRepositories()
                }
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount: Int = reposLayoutManager.itemCount
                val lastVisibleItemIndex: Int = reposLayoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemIndex == totalItemCount -1) {
                    updateScroll()
                }
            }
        })
    }
}
