package com.example.checkpoint2.activities.avatarsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.checkpoint2.adapter.AdapterAvatar
import com.example.checkpoint2.data.DatasourceAvatar
import com.example.checkpoint2.databinding.ActivityAvatarListBinding

class AvatarListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAvatarListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize data.
        val myDataset2 = DatasourceAvatar().loadAvatars()

        val recyclerView2 = binding.recyclerView
        recyclerView2.adapter = AdapterAvatar(this, myDataset2)


        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView2.setHasFixedSize(true)
    }
}