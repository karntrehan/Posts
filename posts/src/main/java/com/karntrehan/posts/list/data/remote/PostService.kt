package com.karntrehan.posts.list.data.remote

import com.karntrehan.posts.list.data.local.Post
import io.reactivex.Single
import retrofit2.http.GET

interface PostService {
    @GET("/posts/")
    fun getPosts(): Single<List<Post>>
}