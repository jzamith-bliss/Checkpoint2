package com.example.checkpoint2.activities.emojiList

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.checkpoint2.adapter.AdapterEmojis
import com.example.checkpoint2.databinding.ActivityEmojiListBinding

class EmojiListActivity : AppCompatActivity() {
    private val viewModel: EmojiListViewModel by viewModels()

/*    private val viewModel: EmojiListViewModel by lazy {
        val activity = requireNotNull(this) {}
        ViewModelProvider(this, EmojiListViewModel.Factory(this.application))[EmojiListViewModel::class.java]
    }*/
    private lateinit var binding: ActivityEmojiListBinding
    private lateinit var adapter: AdapterEmojis

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmojiListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.recyclerView
        binding.viewModel = viewModel

        //viewModel.emojis.observe(this){}

        //viewModel.currentEmojiList.observe(this) {
                //adapter = AdapterEmojis(this, viewModel.currentEmojiList, this)
        //}
        viewModel.initializeEmojiListData { adapter.notifyDataSetChanged() }

        adapter = AdapterEmojis(this, viewModel.emojiList)
        recyclerView.adapter = adapter
        Log.v("SIZE", viewModel.emojiList.size.toString())
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true)


        //swipe refresh layout
        val refresh : SwipeRefreshLayout = binding.swipeRefresh
        //add listener for refresh
        refresh.setOnRefreshListener {
            //refresh data with original dataset
            //viewModel.refreshData()
            //notify the adapter about the data set changes
            adapter.notifyDataSetChanged()
            //stop refreshing
            refresh.isRefreshing = false
        }

    }

     /*override fun onItemClick(position: Int) {
        //viewModel.onEmojiItemClick(position)
        adapter.notifyItemRemoved(position)
    }*/

}