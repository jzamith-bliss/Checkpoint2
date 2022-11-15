package com.example.checkpoint2.activities.googleRepos

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.checkpoint2.adapter.AdapterRepos
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.checkpoint2.activities.avatarsList.AvatarListViewModel
import com.example.checkpoint2.activities.main.MainActivityViewModel
import com.example.checkpoint2.databinding.ActivityGoogleReposBinding
import com.example.checkpoint2.databinding.ListItemGoogleBinding

class GoogleReposActivity : AppCompatActivity() {
    private val viewModel: GoogleReposViewModel by viewModels()
    private lateinit var binding: ActivityGoogleReposBinding
    private lateinit var adapter: AdapterRepos

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleReposBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.recyclerView
        binding.viewModel = viewModel

        viewModel.initializeReposData { adapter.notifyDataSetChanged() }

        adapter = AdapterRepos(this, viewModel.list)
        recyclerView.adapter = adapter

        //viewModel.repos.observe(this){}

/*        viewModel.reposList.observe(this) {
            updateRepos -> bindingListItem.itemTitle.text = updateRepos.repoName
        }*/

        //viewModel.reposList

    }
}