package com.karntrehan.posts.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karntrehan.posts.R
import com.karntrehan.posts.details.DetailsActivity
import com.karntrehan.posts.list.data.PostWithUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_item.view.*

class ListAdapter(private val picasso: Picasso) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var data = emptyList<PostWithUser>()
    var interactor: PostInteractor? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.post_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder?, position: Int) {
        holder?.bind(data[position], picasso)
        holder?.itemView?.setOnClickListener { view -> interactor?.postClicked(view.tag as Int) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<PostWithUser>?) {
        this.data = data ?: this.data
        notifyDataSetChanged()
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(post: PostWithUser, picasso: Picasso) {
            with(post) {
                itemView.tvTitle.text = postTitle
                itemView.tvBody.text = getFormattedPostBody()
                itemView.tvAuthorName.text = userName
                picasso.load(getAvatarPhoto()).into(itemView.ivAvatar)
                itemView.tag = postId
            }
        }
    }

    interface PostInteractor {
        fun postClicked(postId: Int)
    }
}