package com.example.checkpoint2.network
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.example.checkpoint2.R
import com.example.checkpoint2.activities.main.EmojiApiStatus

@BindingAdapter("emojiUrl")
fun bindEmojiImage(emojiImageView: ImageView, emojiUrl: String?) {
    emojiUrl?.let {
        val emojiUri = emojiUrl.toUri().buildUpon().scheme("https").build()
        //emojiImageView.load(emojiUri)

        emojiImageView.load(emojiUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }

    }
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