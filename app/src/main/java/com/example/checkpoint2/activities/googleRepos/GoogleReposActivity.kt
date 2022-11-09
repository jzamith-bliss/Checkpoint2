package com.example.checkpoint2.activities.googleRepos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.checkpoint2.adapter.AdapterRepos
import com.example.checkpoint2.data.DatasourceRepos
import com.example.checkpoint2.databinding.ActivityGoogleReposBinding

class GoogleReposActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoogleReposBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleReposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize data.
        val myDataset = DatasourceRepos().loadRepos()

        val recyclerView = binding.recyclerView
        recyclerView.adapter = AdapterRepos(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }
}