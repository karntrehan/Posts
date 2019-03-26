package com.karntrehan.posts.list

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karntrehan.posts.R
import com.karntrehan.posts.commons.data.PostWithUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_item.view.*

class PostListAdapter(private val picasso: Picasso)
    : ListAdapter<PostWithUser, PostListAdapter.ListViewHolder>(PostWithUserDC()) {

    var interaction: Interaction? = null

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) = ListViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false), interaction)

    override fun onBindViewHolder(
            holder: ListViewHolder,
            position: Int
    ) = holder.bind(getItem(position), picasso)

    fun swapData(data: List<PostWithUser>) {
        submitList(data.toMutableList())
    }

    inner class ListViewHolder(
            itemView: View,
            private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val clicked = getItem(adapterPosition)
            interaction?.postClicked(clicked, itemView.tvTitle, itemView.tvBody, itemView.tvAuthorName, itemView.ivAvatar)
        }

        fun bind(item: PostWithUser, picasso: Picasso) = with(itemView) {
            tvTitle.text = item.postTitle
            tvBody.text = item.getFormattedPostBody()
            tvAuthorName.text = item.userName
            picasso.load(item.getAvatarPhoto())
                    .into(itemView.ivAvatar)

            //SharedItem transition
            ViewCompat.setTransitionName(tvTitle, item.postTitle)
            ViewCompat.setTransitionName(tvBody, item.postBody)
            ViewCompat.setTransitionName(tvAuthorName, item.userName)
            ViewCompat.setTransitionName(ivAvatar, item.getAvatarPhoto())
        }
    }

    interface Interaction {
        fun postClicked(
                post: PostWithUser,
                tvTitle: TextView,
                tvBody: TextView,
                tvAuthorName: TextView,
                ivAvatar: ImageView)
    }

    private class PostWithUserDC : DiffUtil.ItemCallback<PostWithUser>() {
        override fun areItemsTheSame(oldItem: PostWithUser, newItem: PostWithUser) = oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem: PostWithUser, newItem: PostWithUser) = oldItem == newItem
    }
}