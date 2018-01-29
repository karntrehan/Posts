package com.karntrehan.posts.list.data.remote

import com.karntrehan.posts.list.data.local.Post
import io.reactivex.Flowable
import io.reactivex.Single
import org.intellij.lang.annotations.Flow
import retrofit2.http.GET

interface PostService {
    @GET("/posts/")
    fun getPosts(): Flowable<List<Post>>
}