package com.karntrehan.posts.list;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.karntrehan.posts.base.callback.StatefulCallback;
import com.karntrehan.posts.list.ListContract.Model;
import com.karntrehan.posts.list.entity.Post;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.os.Looper.prepare;

/**
 * Created by karn on 03-06-2017.
 */

public class ListModel implements Model {

    private static final String TAG = "ListModel";
    private ListService listService;
    private StorIOSQLite storIOSQLite;

    public ListModel(Retrofit retrofit, StorIOSQLite storIOSQLite) {
        listService = retrofit.create(ListService.class);
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public void loadPosts(final StatefulCallback<List<Post>> statefulCallback) {
        //Load local posts first!
        statefulCallback.onSuccessLocal(storIOSQLite
                .get()
                .listOfObjects(Post.class)
                .withQuery(Query.builder()
                        .table(Post.TABLE_NAME)
                        .build())
                .prepare()
                .executeAsBlocking());

        //Go to server now
        Call<List<Post>> call = listService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call,
                                   @NonNull Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    final List<Post> posts = response.body();
                    Log.d(TAG, "onResponse: " + posts);
                    if (posts != null)
                        storIOSQLite
                                .put()
                                .objects(posts)
                                .useTransaction(true)
                                .prepare()
                                .executeAsBlocking();
                    statefulCallback.onSuccessSync(posts);
                } else statefulCallback.onValidationError(response);
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call,
                                  @NonNull Throwable t) {
                statefulCallback.onFailure(t);
            }
        });
    }
}
