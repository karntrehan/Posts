package com.karntrehan.posts.list;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karntrehan.posts.PostApp;
import com.karntrehan.posts.R;
import com.karntrehan.posts.base.Constants;
import com.karntrehan.posts.databinding.ActivityListBinding;
import com.karntrehan.posts.details.DetailsActivity;
import com.karntrehan.posts.list.entity.Post;

import java.util.List;

import javax.inject.Inject;

public class ListActivity extends AppCompatActivity implements ListContract.View {

    ActivityListBinding binding;

    @Inject
    ListContract.Presenter presenter;
    @Inject
    ListAdapter listAdapter;

    private static final String TAG = "ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);

        ((PostApp) this.getApplication()).createListComponent().inject(this);

        binding.srlPosts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getPosts();
            }
        });

        binding.rvPosts.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        presenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        presenter.create();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        presenter.destroy();
    }

    ////////////////////////////////////////////////////////////////////
    //// View methods
    ////////////////////////////////////////////////////////////////////

    @Override
    public void showLoading(boolean loading) {
        if (loading)
            binding.srlPosts.setRefreshing(true);
        else binding.srlPosts.setRefreshing(false);
    }

    @Override
    public void showPosts(List<Post> posts) {
        listAdapter.setPosts(posts);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void showPostDetail(int position, Post post, ListAdapter.ViewHolder holder) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.POST_SEL, post);
        intent.putExtra(Constants.TITLE_TRANSITION_NAME, ViewCompat.getTransitionName(holder.binding.tvTitle));
        intent.putExtra(Constants.BODY_TRANSITION_NAME, ViewCompat.getTransitionName(holder.binding.tvBody));

        Pair<View, String> p1 = Pair.create((View) holder.binding.tvTitle, ViewCompat.getTransitionName(holder.binding.tvTitle));
        Pair<View, String> p2 = Pair.create((View) holder.binding.tvBody, ViewCompat.getTransitionName(holder.binding.tvBody));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, p1, p2);

        startActivity(intent, options.toBundle());
    }
}
