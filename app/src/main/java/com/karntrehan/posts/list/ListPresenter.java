package com.karntrehan.posts.list;

import com.karntrehan.posts.list.entity.Post;

/**
 * Created by karn on 03-06-2017.
 */

public class ListPresenter implements ListContract.Presenter {

    private ListContract.View view;
    private ListContract.Model model;

    public ListPresenter(ListContract.Model model) {
        this.model = model;
    }

    @Override
    public void create() {

    }

    @Override
    public void setView(ListContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void getPosts() {

    }

    @Override
    public void postClicked(Post post) {

    }
}
