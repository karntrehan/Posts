package com.karntrehan.posts.details.model

import com.karntrehan.posts.commons.data.local.Comment
import com.karntrehan.posts.commons.data.remote.PostService
import io.reactivex.Flowable

class DetailsRemoteData(private val postService: PostService) : DetailsDataContract.Remote {

    override fun getCommentsForPost(postId: Int): Flowable<List<Comment>> {
        return postService.getComments(postId)
    }
}