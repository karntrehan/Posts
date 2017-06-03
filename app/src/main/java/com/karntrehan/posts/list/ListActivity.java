package com.karntrehan.posts.list;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.karntrehan.posts.PostApp;
import com.karntrehan.posts.R;
import com.karntrehan.posts.databinding.ActivityListBinding;
import com.karntrehan.posts.list.entity.Post;

import java.util.List;

import javax.inject.Inject;

public class ListActivity extends AppCompatActivity implements ListContract.View {

    ActivityListBinding binding;

    @Inject
    ListContract.Presenter presenter;
    @Inject
    ListAdapter listAdapter;

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
        presenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.create();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
}
