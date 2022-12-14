package com.example.checkpoint2.network

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.checkpoint2.R
import com.example.checkpoint2.activities.main.EmojiApiStatus
import com.example.checkpoint2.adapter.AdapterAvatar

import com.example.checkpoint2.adapter.AdapterEmojis
import com.example.checkpoint2.adapter.AdapterRepos
import com.example.checkpoint2.model.Avatar
import com.example.checkpoint2.model.Emoji
import com.example.checkpoint2.model.Repos

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Emoji>?) {
    val adapter = recyclerView.adapter as AdapterEmojis
    adapter.submitList(data)
}

@BindingAdapter("listReposData")
fun bindRecyclerViewRepos(recyclerView: RecyclerView, data: List<Repos>?) {
    val adapter = recyclerView.adapter as AdapterRepos
    adapter.submitList(data)
}

@BindingAdapter("listAvatarData")
fun bindRecyclerViewAvatar(recyclerView: RecyclerView, data: List<Avatar>?) {
    val adapter = recyclerView.adapter as AdapterAvatar
    adapter.submitList(data)
}

@BindingAdapter("emojiUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("avatarUrl")
fun bindAvatar(avatar: ImageView, avatarUrl: String?) {
    avatarUrl?.let {
        val imgUri = avatarUrl.toUri().buildUpon().scheme("https").build()
        avatar.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("reposName")
fun bindRepos(textView: TextView, repoName: String?) {
    textView.text = repoName
}

@BindingAdapter("emojiApiStatus")
fun bindStatus(statusImageView: ImageView, status: EmojiApiStatus?) {
    when (status) {
        EmojiApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        EmojiApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        EmojiApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {}
    }
}
