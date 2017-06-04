package com.karntrehan.posts.list;

import com.karntrehan.posts.list.entity.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by karn on 03-06-2017.
 */

public interface ListService {
    @GET("/posts/")
    Call<List<Post>> getPosts();
}
