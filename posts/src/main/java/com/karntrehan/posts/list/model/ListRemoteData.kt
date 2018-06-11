package com.karntrehan.posts.list.model

import com.karntrehan.posts.commons.data.remote.PostService

class ListRemoteData(private val postService: PostService) : ListDataContract.Remote {

    override fun getUsers() = postService.getUsers()

    override fun getPosts() = postService.getPosts()
}