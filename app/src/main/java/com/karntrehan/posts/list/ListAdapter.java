package com.karntrehan.posts.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karntrehan.posts.R;
import com.karntrehan.posts.databinding.PostItemBinding;
import com.karntrehan.posts.list.entity.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karn on 03-06-2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ListContract.Presenter presenter;
    private List<Post> posts;

    public ListAdapter(ListContract.Presenter presenter) {
        this.presenter = presenter;
        posts = new ArrayList<>();
    }

    protected void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PostItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.post_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Post post = posts.get(position);
        holder.binding.tvTitle.setText(post.getPostTitle());
        holder.binding.tvBody.setText(post.getFormattedPostBody());

        //SharedItem transition
        ViewCompat.setTransitionName(holder.binding.tvTitle, post.getPostTitle());
        ViewCompat.setTransitionName(holder.binding.tvBody, post.getFormattedPostBody());

        holder.binding.cvPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.postClicked(position,post,holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        PostItemBinding binding;

        public ViewHolder(PostItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
