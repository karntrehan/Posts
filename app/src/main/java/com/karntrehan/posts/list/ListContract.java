package com.karntrehan.posts.list;

import com.karntrehan.posts.base.callback.ValidationCallback;
import com.karntrehan.posts.list.entity.Post;

import java.util.List;

/**
 * Created by karn on 03-06-2017.
 */

public interface ListContract {
    interface Presenter {
        void create();

        void setView(View view);

        void destroy();

        void getPosts();

        void postClicked(Post post);
    }

    interface View {
        void showloading(boolean loading);

        void showPosts(List<Object> posts);
    }

    interface Model {
        void loadPosts(ValidationCallback<List<Object>> validationCallback);
    }
}
