package com.karntrehan.posts.list

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.karntrehan.posts.R
import com.karntrehan.posts.commons.data.PostWithUser
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

        if (holder?.itemView != null)
            holder.itemView.setOnClickListener { view ->
                with(view) {
                    interactor?.postClicked(data[position], tvTitle, tvBody, tvAuthorName, ivAvatar)
                }
            }
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

                //SharedItem transition
                ViewCompat.setTransitionName(itemView.tvTitle, postTitle);
                ViewCompat.setTransitionName(itemView.tvBody, postBody);
                ViewCompat.setTransitionName(itemView.tvAuthorName, userName);
                ViewCompat.setTransitionName(itemView.ivAvatar, getAvatarPhoto());
            }
        }
    }

    interface PostInteractor {
        fun postClicked(post: PostWithUser, tvTitle: TextView, tvBody: TextView, tvAuthorName: TextView, ivAvatar: ImageView)
    }
}