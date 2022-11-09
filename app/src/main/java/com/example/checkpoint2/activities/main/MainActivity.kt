package com.example.checkpoint2.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.checkpoint2.databinding.ActivityMainBinding
import com.example.checkpoint2.activities.avatarsList.AvatarListActivity
import com.example.checkpoint2.activities.emojiList.EmojiListActivity
import com.example.checkpoint2.activities.googleRepos.GoogleReposActivity

class MainActivity : AppCompatActivity() {
/*    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MainActivityViewModel.Factory(activity.application))
            .get(MainActivityViewModel::class.java)
    }*/


    private val viewModel: MainActivityViewModel by lazy {
        val activity = requireNotNull(this) {}
        //ViewModelProvider(this, MainViewModel.Factory(activity.application))[MainViewModel::class.java]
        ViewModelProvider(this, MainActivityViewModel.Factory(this.application))[MainActivityViewModel::class.java]
    }
    //private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.emojis.observe(this){}

        viewModel.currentRandomEmoji.observe(this) {
            //binding.imageView.setImageResource(it)
                updatedEmoji -> binding.imageView.load(updatedEmoji.emojiUrl.toUri().buildUpon().scheme("https").build())
        }

        viewModel.initializeMainData()

        //OnClick for roll button
        binding.buttonRandomEmoji.setOnClickListener { onRandomEmoji() }

        // Set initial image as like image
        //val emojiImage: ImageView = findViewById(R.id.imageView)
        //emojiImage.setImageResource(R.drawable.like)

        // Set OnClick event to Emoji List button
        binding.buttonEmojiList.setOnClickListener {
            val intent = Intent (this, EmojiListActivity::class.java)
            startActivity(intent)
        }

        // Set OnClick event to Avatar List button
        binding.buttonAvatarList.setOnClickListener {
            val intent = Intent (this, AvatarListActivity::class.java)
            startActivity(intent)
        }

        // Set OnClick event to Google Repos button
        binding.buttonGoogleRepos.setOnClickListener {
            val intent = Intent (this, GoogleReposActivity::class.java)
            startActivity(intent)
        }


    }

    private fun onRandomEmoji() {
        viewModel.randomImg()
    }


}
