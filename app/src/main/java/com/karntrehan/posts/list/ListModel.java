package com.karntrehan.posts.list;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.karntrehan.posts.base.callback.StatefulCallback;
import com.karntrehan.posts.list.ListContract.Model;
import com.karntrehan.posts.list.entity.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by karn on 03-06-2017.
 */

public class ListModel implements Model {

    private static final String TAG = "ListModel";
    private ListService listService;

    public ListModel(Retrofit retrofit) {
        listService = retrofit.create(ListService.class);
    }

    @Override
    public void loadPosts(final StatefulCallback<List<Post>> statefulCallback) {
        Call<List<Post>> call = listService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call,
                                   @NonNull Response<List<Post>> response) {
                if (response.isSuccessful())
                    statefulCallback.onSuccessSync(response.body());
                else statefulCallback.onValidationError(response);
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call,
                                  @NonNull Throwable t) {
                statefulCallback.onFailure(t);
            }
        });
    }
}
