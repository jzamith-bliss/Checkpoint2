package com.example.checkpoint2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.checkpoint2.databinding.ListItemAvatarBinding
import com.example.checkpoint2.model.Avatar

class AdapterAvatar(
    private val context: Context,
    private val dataset: List<Avatar>,
    private val mListener: AvatarClickListener
) : ListAdapter<Avatar, AdapterAvatar.ItemViewHolder>(DiffCallback) {
    // Item click Listener - https://www.youtube.com/watch?v=wKFJsrdiGS8
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.

    class ItemViewHolder(val binding: ListItemAvatarBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(avatarPhoto: Avatar, listener : AvatarClickListener) {
            binding.avatar = avatarPhoto
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.avatarClick = listener
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Avatar>() {
        override fun areItemsTheSame(oldItem: Avatar, newItem: Avatar): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Avatar, newItem: Avatar): Boolean {
            return oldItem.avatarUrl == newItem.avatarUrl
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
            ListItemAvatarBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val avatarPhoto = getItem(position)
        holder.bind(avatarPhoto, mListener)
    }

    override fun getItemCount(): Int = dataset.size

    interface AvatarClickListener {
        fun onAvatarClicked(avatar: Avatar)
    }
}