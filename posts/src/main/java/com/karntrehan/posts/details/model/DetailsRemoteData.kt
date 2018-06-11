package com.karntrehan.posts.details.model

import com.karntrehan.posts.commons.data.remote.PostService

class DetailsRemoteData(private val postService: PostService) : DetailsDataContract.Remote {

    override fun getCommentsForPost(postId: Int) = postService.getComments(postId)

}