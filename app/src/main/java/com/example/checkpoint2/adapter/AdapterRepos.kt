package com.example.checkpoint2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.checkpoint2.databinding.ListItemGoogleBinding
import com.example.checkpoint2.model.Repos

class AdapterRepos(
    private val context: Context,
    private val dataset: List<Repos>
) : ListAdapter<Repos, AdapterRepos.ItemViewHolder>(DiffCallback) {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(val binding: ListItemGoogleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repoName: Repos) {
            binding.reposText = repoName
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Repos>() {
        override fun areItemsTheSame(oldItem: Repos, newItem: Repos): Boolean {
            return oldItem.repoName == newItem.repoName
        }

        override fun areContentsTheSame(oldItem: Repos, newItem: Repos): Boolean {
            return oldItem.repoName == newItem.repoName
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterRepos.ItemViewHolder {
        // create a new view
        return AdapterRepos.ItemViewHolder(
            ListItemGoogleBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        //holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.bind(item)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}