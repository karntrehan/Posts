package com.karntrehan.posts.details.model

import com.karntrehan.posts.commons.data.local.Comment
import com.karntrehan.posts.commons.data.local.PostDb
import com.karntrehan.posts.core.extensions.performOnBackOutOnMain
import com.karntrehan.posts.core.networking.Scheduler
import io.reactivex.Completable
import io.reactivex.Flowable

class DetailsLocalData(private val postDb: PostDb, private val scheduler: Scheduler) : DetailsDataContract.Local {

    override fun getCommentsForPost(postId: Int): Flowable<List<Comment>> {
        return postDb.commentDao().getForPost(postId)
    }

    override fun saveComments(comments: List<Comment>) {
        Completable.fromAction {
            postDb.commentDao().insertAll(comments)
        }
                .performOnBackOutOnMain(scheduler)
                .subscribe()
    }
}