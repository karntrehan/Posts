package com.karntrehan.posts.list;

import android.util.Log;

import com.karntrehan.posts.base.callback.StatefulCallback;
import com.karntrehan.posts.list.entity.Post;

import java.util.List;

import retrofit2.Response;


/**
 * Created by karn on 03-06-2017.
 */

public class ListPresenter implements ListContract.Presenter {

    private ListContract.View view;
    private ListContract.Model model;

    private List<Post> posts;
    private static final String TAG = "ListPresenter";

    public ListPresenter(ListContract.Model model) {
        this.model = model;
    }

    @Override
    public void create() {
        if (posts != null)
            view.showPosts(posts);
        else getPosts();
    }

    @Override
    public void setView(ListContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void getPosts() {
        Log.d(TAG, "getPosts: ");
        view.showLoading(true);
        model.loadPosts(new StatefulCallback<List<Post>>() {
            @Override
            public void onSuccessLocal(List<Post> response) {
                posts = response;
                //Log.d(TAG, "onSuccessLocal: " + posts.toString());
                view.showPosts(posts);
            }

            @Override
            public void onSuccessSync(List<Post> response) {
                posts = response;
                Log.d(TAG, "onSuccessSync: " + posts.toString());
                if (isViewReady()) {
                    view.showLoading(false);
                    view.showPosts(posts);
                }
            }

            @Override
            public void onValidationError(Response response) {
                //Handle the validation error sent by the server using ErrorParser!
                if (!isViewReady())
                    return;

                view.showLoading(false);
                view.showError("Error, Server returned: " + response.code());
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (!isViewReady())return;

                view.showLoading(false);
                view.showError("Error: "+throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public void postClicked(Post post) {
        Log.d(TAG, "postClicked: " + post.toString());
    }

    private boolean isViewReady() {
        return view != null;
    }
}
