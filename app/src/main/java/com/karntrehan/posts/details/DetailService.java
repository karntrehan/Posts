package com.karntrehan.posts.details;

import com.karntrehan.posts.details.entity.Comment;
import com.karntrehan.posts.details.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by karn on 03-06-2017.
 */

public interface DetailService {
    @GET("/users/")
    Call<List<User>> getUser(@Query("id") Integer id);

    @GET("/comments/")
    Call<List<Comment>> getComments(@Query("postId") Integer id);
}
