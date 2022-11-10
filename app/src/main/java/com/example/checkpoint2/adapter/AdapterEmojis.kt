package com.example.checkpoint2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.checkpoint2.databinding.ListItemEmojiBinding
import com.example.checkpoint2.model.Emoji

class AdapterEmojis(
    private val context: Context,
    private val dataset: List<Emoji>,
    //private val listener: AdapterView.OnItemClickListener
) : ListAdapter<Emoji, AdapterEmojis.ItemViewHolder>(DiffCallback) {
    // Item click Listener - https://www.youtube.com/watch?v=wKFJsrdiGS8
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.

    class ItemViewHolder(
        private var binding: ListItemEmojiBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(emojiPhoto: Emoji) {
            binding.emoji = emojiPhoto
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Emoji>() {
        override fun areItemsTheSame(oldItem: Emoji, newItem: Emoji): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Emoji, newItem: Emoji): Boolean {
            return oldItem.emojiUrl == newItem.emojiUrl
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        // create a new view
        return ItemViewHolder(
            ListItemEmojiBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //val item = dataset.value?.get(position)
        //val item = dataset[position]
        //holder.imageView.setImageResource(item.imageResourceId)
        val emojiPhoto = getItem(position)
        holder.bind(emojiPhoto)
    }
}