package com.example.checkpoint2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.checkpoint2.R
import com.example.checkpoint2.model.Emoji

class AdapterEmojis(
    private val context: Context,
    //private val dataset: LiveData<List<Emoji>>,
    private val dataset: List<Emoji>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<AdapterEmojis.ItemViewHolder>() {
    // Item click Listener - https://www.youtube.com/watch?v=wKFJsrdiGS8
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener{
        val imageView: ImageView = view.findViewById(R.id.item_image)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position !=
                RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_emoji, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //val item = dataset.value?.get(position)
        val item = dataset[position]
        //holder.imageView.setImageResource(item.imageResourceId)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
    //override fun getItemCount() = dataset.value!!.size

    interface OnItemClickListener {
        fun onItemClick(position:Int)
    }
}