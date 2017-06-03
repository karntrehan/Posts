package com.karntrehan.posts.details;

import com.karntrehan.posts.base.callback.StatefulCallback;
import com.karntrehan.posts.list.entity.Post;

/**
 * Created by karn on 03-06-2017.
 */

public interface DetailsContract {
    interface Presenter {
        void setView(View view);

        void destroy();

        void getPostDetails(Post post);
    }

    interface View {
        void showPostDetails(Details details);

        void showLoading(boolean loading);

        void showError(String error);
    }

    interface Model {
        void loadPostDetails(Long postId, StatefulCallback<Details> detailsStatefulCallback);
    }
}
