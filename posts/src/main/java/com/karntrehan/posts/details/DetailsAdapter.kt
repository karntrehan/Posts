package com.karntrehan.posts.details

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karntrehan.posts.R
import com.karntrehan.posts.commons.data.local.Comment
import kotlinx.android.synthetic.main.comment_item.view.*

class DetailsAdapter(private val interaction: Interaction? = null) :
        ListAdapter<Comment, DetailsAdapter.DetailsViewHolder>(CommentDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DetailsViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.comment_item, parent, false), interaction
    )

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) = holder.bind(getItem(position))

    fun swapData(data: List<Comment>) {
        submitList(data.toMutableList())
    }

    inner class DetailsViewHolder(itemView: View, private val interaction: Interaction?)
        : RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val clicked = getItem(adapterPosition)
            interaction?.commentClicked(clicked)
        }

        fun bind(item: Comment) = with(itemView) {
            tvComment.text = item.body
            tvAuthor.text = "- ${item.name}"
        }
    }

    interface Interaction {
        fun commentClicked(model: Comment)
    }

    private class CommentDC : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(
                oldItem: Comment,
                newItem: Comment
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
                oldItem: Comment,
                newItem: Comment
        ) = oldItem == newItem
    }
}