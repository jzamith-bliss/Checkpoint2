package com.example.checkpoint2.activities.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.checkpoint2.databinding.ActivityMainBinding
import com.example.checkpoint2.activities.avatarsList.AvatarListActivity
import com.example.checkpoint2.activities.emojiList.EmojiListActivity
import com.example.checkpoint2.activities.googleRepos.GoogleReposActivity
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        val activity = requireNotNull(this) {}
        //ViewModelProvider(this, MainViewModel.Factory(activity.application))[MainViewModel::class.java]
        ViewModelProvider(this, MainActivityViewModel.Factory(this.application))[MainActivityViewModel::class.java]
    }
    //private val viewModel: MainActivityViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.emojis.observe(this){}

        val sharedPreferences = getSharedPreferences("mainImage", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        viewModel.currentRandomEmoji.observe(this) {
            updatedEmoji -> binding.imageView.load(updatedEmoji.emojiUrl.toUri().buildUpon().scheme("https").build())
            editor.remove("currentAvatar").apply()
            editor.putString("currentEmoji",updatedEmoji.emojiUrl).apply()
        }

        viewModel.usernameAvatar.observe(this) {
                updatedAvatar -> binding.imageView.load(updatedAvatar.avatarUrl.toUri().buildUpon().scheme("https").build())
            editor.remove("currentEmoji").apply()
            editor.putString("currentAvatar",updatedAvatar.avatarUrl).apply()
        }

        val emoji = sharedPreferences.getString("currentEmoji",null)
        val avatar = sharedPreferences.getString("currentAvatar",null)

        viewModel.initializeMainData(emoji, avatar)



/*        fun latestImageView() {
           val emoji = sharedPreferences.getString("currentEmoji","defaultStringIfNothingFound")
            Log.v("LOG", "emoji $emoji")
            val avatar = sharedPreferences.getString("currentAvatar","defaultStringIfNothingFound")
            Log.v("LOG", "avatar $avatar")
        }*/

        //OnClick for roll button
        binding.buttonRandomEmoji.setOnClickListener { onRandomEmoji() }

        // Set OnClick event to Emoji List button
        binding.buttonEmojiList.setOnClickListener {
            val intent = Intent (this, EmojiListActivity::class.java)
            startActivity(intent)
        }

        // Bind textView to textInputLayout2
        val textView: TextInputLayout = binding.textInputLayout2

        // Bind floatingActionButton.setOnClickListener to receive a function that listens to the textView
        binding.floatingActionButton.setOnClickListener {
            val string = textView.editText?.text.toString()
            viewModel.getGitHubUsername(string.trim(), this)
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
        viewModel.setNewRandomEmoji()
    }
}
