package com.karntrehan.posts.list;

import com.karntrehan.posts.base.callback.ValidationCallback;
import com.karntrehan.posts.list.ListContract.Model;

import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by karn on 03-06-2017.
 */

public class ListModel implements Model {

    private final Retrofit retrofit;

    public ListModel(Retrofit retrofit) {
        this.retrofit = retrofit;

    }

    @Override
    public void loadPosts(ValidationCallback<List<Object>> validationCallback) {

    }
}
