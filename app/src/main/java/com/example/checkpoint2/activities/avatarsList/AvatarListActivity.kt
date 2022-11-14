package com.example.checkpoint2.activities.avatarsList

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.checkpoint2.adapter.AdapterAvatar
import com.example.checkpoint2.databinding.ActivityAvatarListBinding
import com.example.checkpoint2.model.Avatar

class AvatarListActivity : AppCompatActivity(), AdapterAvatar.AvatarClickListener {
    private val viewModel: AvatarListViewModel by viewModels()
    private lateinit var binding: ActivityAvatarListBinding
    private  lateinit var adapter: AdapterAvatar

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView2 = binding.recyclerView
        binding.viewModel = viewModel

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
        viewModel.onAvatarItemClick(avatar, { adapter.notifyDataSetChanged() })
    }
}