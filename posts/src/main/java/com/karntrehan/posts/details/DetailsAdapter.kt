package com.karntrehan.posts.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karntrehan.posts.R
import com.karntrehan.posts.commons.data.local.Comment
import kotlinx.android.synthetic.main.comment_item.view.*

class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {
    private var data = emptyList<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DetailsViewHolder {
        return DetailsViewHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.comment_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailsViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<Comment>?) {
        this.data = data ?: this.data
        notifyDataSetChanged()
    }

    class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(comment: Comment) {
            with(comment) {
                itemView.tvComment.text = body
                itemView.tvAuthor.text = "- $name"
            }
        }
    }
}