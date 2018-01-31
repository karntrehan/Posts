package com.karntrehan.posts.list.data.remote

import com.karntrehan.posts.list.data.local.Post
import com.karntrehan.posts.list.data.local.User
import io.reactivex.Flowable
import io.reactivex.Observable
import org.intellij.lang.annotations.Flow
import retrofit2.http.GET

interface PostService {
    @GET("/posts/")
    fun getPosts(): Flowable<List<Post>>

    @GET("/users/")
    fun getUsers(): Flowable<List<User>>
}