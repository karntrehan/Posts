package com.karntrehan.posts.list.model

import com.karntrehan.posts.commons.data.local.Post
import com.karntrehan.posts.commons.data.local.User
import com.karntrehan.posts.commons.data.remote.PostService
import io.reactivex.Flowable

class ListRemoteData(private val postService: PostService) : ListDataContract.Remote {

    override fun getUsers(): Flowable<List<User>> {
        return postService.getUsers()
    }

    override fun getPosts(): Flowable<List<Post>> {
        return postService.getPosts()
    }
}