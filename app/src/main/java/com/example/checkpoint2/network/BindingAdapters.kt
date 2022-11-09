package com.example.checkpoint2.network
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("emojiUrl")
fun bindEmojiImage(emojiImageView: ImageView, emojiUrl: String?) {
    emojiUrl?.let {
        val emojiUri = emojiUrl.toUri().buildUpon().scheme("https").build()
        emojiImageView.load(emojiUri)
        /*
        emojiImageView.load(emojiUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
        */
    }
}