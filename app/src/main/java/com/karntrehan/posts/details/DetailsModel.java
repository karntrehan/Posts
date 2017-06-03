package com.karntrehan.posts.details;

import android.support.annotation.NonNull;

import com.karntrehan.posts.base.callback.StatefulCallback;
import com.karntrehan.posts.details.entity.Comment;
import com.karntrehan.posts.details.entity.User;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by karn on 03-06-2017.
 */

public class DetailsModel implements DetailsContract.Model {

    private static final String TAG = "DetailsModel";
    private DetailService detailService;
    private StorIOSQLite storIOSQLite;

    public DetailsModel(Retrofit retrofit, StorIOSQLite storIOSQLite) {
        detailService = retrofit.create(DetailService.class);
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public void loadPostDetails(Long postId, StatefulCallback<Details> detailsStatefulCallback) {
        Call<User> getUserCall = detailService.getUser(postId);
        getUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

            }
        });

        Call<List<Comment>> getCommentsCall = detailService.getComments(postId);
        getCommentsCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call,
                                   @NonNull Response<List<Comment>> response) {

            }

            @Override
            public void onFailure(@NonNull Call<List<Comment>> call, @NonNull Throwable t) {

            }
        });
    }
}
