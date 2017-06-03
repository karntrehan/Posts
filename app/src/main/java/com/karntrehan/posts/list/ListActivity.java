package com.karntrehan.posts.list;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.karntrehan.posts.PostApp;
import com.karntrehan.posts.R;
import com.karntrehan.posts.databinding.ActivityListBinding;

import java.util.List;

import javax.inject.Inject;

public class ListActivity extends AppCompatActivity implements ListContract.View {

    ActivityListBinding binding;

    @Inject
    ListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);

        ((PostApp) this.getApplication()).createListComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
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
    public void showloading(boolean loading) {

    }

    @Override
    public void showPosts(List<Object> posts) {

    }
}
