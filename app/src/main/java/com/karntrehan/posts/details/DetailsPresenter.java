package com.karntrehan.posts.details;

import android.util.Log;

import com.karntrehan.posts.base.callback.StatefulCallback;
import com.karntrehan.posts.list.entity.Post;

import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by karn on 03-06-2017.
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    private DetailsContract.View view;
    private DetailsContract.Model model;
    Details details;

    private static final String TAG = "DetailsPresenter";

    public DetailsPresenter(DetailsContract.Model model) {
        this.model = model;
    }

    @Override
    public void setView(DetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void getPostDetails(Post post) {
        Log.d(TAG, "getPostDetails: "+post.toString());
        if (details != null)
            view.showPostDetails(details);
        else {
            view.showLoading(true);
            model.loadPostDetails(post, new StatefulCallback<Details>() {
                @Override
                public void onSuccessLocal(Details response) {
                    details = response;
                    if (isViewReady())
                        view.showPostDetails(details);
                }

                @Override
                public void onSuccessSync(Details response) {
                    details = response;
                    if (!isViewReady())
                        return;
                    view.showPostDetails(details);
                    view.showLoading(false);
                }

                @Override
                public void onValidationError(Response response) {
                    if (!isViewReady())
                        return;
                    view.showLoading(false);
                    view.showError("Error, Server returned: " + response.code());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    if (!isViewReady())
                        return;
                    view.showLoading(false);
                    view.showError("Error: " + throwable.getLocalizedMessage());
                }
            });
        }
    }

    private boolean isViewReady() {
        return view != null;
    }
}
