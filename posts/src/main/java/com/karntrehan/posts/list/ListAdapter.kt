package com.karntrehan.posts.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karntrehan.posts.R
import com.karntrehan.posts.list.data.local.Post
import kotlinx.android.synthetic.main.post_item.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var data = emptyList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.post_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<Post>?) {
        this.data = data ?: this.data
        notifyDataSetChanged()
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(post: Post) {
            with(post) {
                itemView.tvTitle.text = postTitle
                itemView.tvBody.text = getFormattedPostBody()
            }
        }
    }
}