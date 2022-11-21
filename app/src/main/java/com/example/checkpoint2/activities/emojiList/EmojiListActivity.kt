package com.example.checkpoint2.activities.emojiList

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.checkpoint2.R
import com.example.checkpoint2.adapter.AdapterEmojis
import com.example.checkpoint2.databinding.ActivityEmojiListBinding
import com.example.checkpoint2.model.Emoji

class EmojiListActivity : AppCompatActivity(),AdapterEmojis.EmojiClickListener {
    private val viewModel: EmojiListViewModel by viewModels()
    private lateinit var binding: ActivityEmojiListBinding
    private lateinit var adapter: AdapterEmojis

    @SuppressLint("NotifyDataSetChanged", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Checkpoint2)
        binding = ActivityEmojiListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.recyclerView
        binding.viewModel = viewModel

        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)

        viewModel.initializeEmojiListData { adapter.notifyDataSetChanged() }

        adapter = AdapterEmojis(this, viewModel.emojiList, this)
        recyclerView.adapter = adapter

        //swipe refresh layout
        val refresh : SwipeRefreshLayout = binding.swipeRefresh
        //add listener for refresh
        refresh.setOnRefreshListener {
            //refresh data with original dataset
            viewModel.refreshData()
            //notify the adapter about the data set changes
            adapter.notifyDataSetChanged()
            //stop refreshing
            refresh.isRefreshing = false
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onEmojiClicked(emoji: Emoji) {
        viewModel.onEmojiItemClick(emoji) { adapter.notifyDataSetChanged() }
    }
}
