package com.example.checkpoint2.activities.avatarsList

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.checkpoint2.R
import com.example.checkpoint2.adapter.AdapterAvatar
import com.example.checkpoint2.databinding.ActivityAvatarListBinding
import com.example.checkpoint2.model.Avatar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AvatarListActivity : AppCompatActivity(), AdapterAvatar.AvatarClickListener {
    private val viewModel: AvatarListViewModel by viewModels()
    private lateinit var binding: ActivityAvatarListBinding
    private  lateinit var adapter: AdapterAvatar

    @SuppressLint("NotifyDataSetChanged", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Checkpoint2)
        binding = ActivityAvatarListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView2 = binding.recyclerView
        binding.viewModel = viewModel

        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)

        viewModel.initializeAvatarListData { adapter.notifyDataSetChanged() }

        adapter = AdapterAvatar(this, viewModel.avatarList, this)
        recyclerView2.adapter = adapter

        //swipe refresh layout
        val refresh : SwipeRefreshLayout = binding.swipeRefresh
        //add listener for refresh
        refresh.setOnRefreshListener {
            //refresh data with original dataset
            viewModel.clearAvatars()
            //notify the adapter about the data set changes
            adapter.notifyDataSetChanged()

            refresh.isRefreshing = false

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onAvatarClicked(avatar: Avatar) {
        viewModel.onAvatarItemClick(avatar) { adapter.notifyDataSetChanged() }
    }
}