package com.karntrehan.posts.details;

import android.support.annotation.NonNull;

import com.karntrehan.posts.base.callback.StatefulCallback;
import com.karntrehan.posts.details.entity.Comment;
import com.karntrehan.posts.details.entity.User;
import com.karntrehan.posts.list.entity.Post;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by karn on 03-06-2017.
 */

public class DetailsModel implements DetailsContract.Model {

    private final DetailService detailService;
    private final StorIOSQLite storIOSQLite;

    public DetailsModel(Retrofit retrofit, StorIOSQLite storIOSQLite) {
        detailService = retrofit.create(DetailService.class);
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public void loadPostDetails(Post post, final StatefulCallback<Details> detailsStatefulCallback) {
        //Fist try to load local details
        final Details details = new Details();

        //Get user for the post
        final User user = storIOSQLite.get().object(User.class).withQuery(Query.builder()
                .table(User.TABLE_NAME)
                .where("user_id = ?").whereArgs(post.getUserId())
                .build())
                .prepare().executeAsBlocking();
        if (user != null) {
            details.setUserName(user.getName());
            details.setUserAvatarFromEmail(user.getEmail());
        }

        //Get comment count for the post
        final long commentCount = storIOSQLite.get().numberOfResults().withQuery(Query.builder()
                .table(Comment.TABLE_NAME)
                .where("post_id = ?")
                .whereArgs(post.getPostId())
                .build())
                .prepare()
                .executeAsBlocking();

        details.setCommentCount(commentCount);
        //Send local response back to the presenter
        detailsStatefulCallback.onSuccessLocal(details);

        //Go to the server to get the user details from the server for this post
        Call<List<User>> getUserCall = detailService.getUser(post.getUserId());
        getUserCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call,
                                   @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    if (users != null && users.size() >= 1) {
                        storIOSQLite
                                .put()
                                .objects(users)
                                .useTransaction(true)
                                .prepare()
                                .executeAsBlocking();
                        details.setUserName(users.get(0).getName());
                        details.setUserAvatarFromEmail(users.get(0).getEmail());
                        detailsStatefulCallback.onSuccessSync(details);
                    }
                } else detailsStatefulCallback.onValidationError(response);
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                detailsStatefulCallback.onFailure(t);
            }
        });

        //Go to the server and get the latest comments from the server for this post
        Call<List<Comment>> getCommentsCall = detailService.getComments(post.getPostId());
        getCommentsCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call,
                                   @NonNull Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    List<Comment> comments = response.body();
                    if (comments != null) {
                        storIOSQLite
                                .put()
                                .objects(comments)
                                .useTransaction(true)
                                .prepare()
                                .executeAsBlocking();
                        details.setCommentCount(comments.size());
                    }
                    detailsStatefulCallback.onSuccessSync(details);
                } else detailsStatefulCallback.onValidationError(response);
            }

            @Override
            public void onFailure(@NonNull Call<List<Comment>> call, @NonNull Throwable t) {
                detailsStatefulCallback.onFailure(t);
            }
        });
    }
}
