package com.karntrehan.posts.details

import com.karntrehan.posts.commons.data.local.Comment
import com.karntrehan.posts.commons.data.local.PostDb
import com.karntrehan.posts.commons.data.remote.PostService
import com.karntrehan.posts.core.extensions.failed
import com.karntrehan.posts.core.extensions.loading
import com.karntrehan.posts.core.extensions.performOnBackOutOnMain
import com.karntrehan.posts.core.extensions.success
import com.mpaani.core.networking.Outcome
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class DetailsRepository(val postDb: PostDb, val postService: PostService) {

    val commentsFetchOutcome: PublishSubject<Outcome<List<Comment>>> = PublishSubject.create<Outcome<List<Comment>>>()

    var remoteFetch = true

    fun fetchCommentsFor(postId: Int?, compositeDisposable: CompositeDisposable) {
        if (postId == null)
            return

        commentsFetchOutcome.loading(true)
        compositeDisposable.add(postDb.commentDao().getForPost(postId)
                .performOnBackOutOnMain()
                .subscribe({ retailers ->
                    commentsFetchOutcome.success(retailers)
                    if (remoteFetch)
                        refreshComments(postId, compositeDisposable)
                    remoteFetch = false
                }, { error -> handleError(error) })
        )
    }

    fun refreshComments(postId: Int, compositeDisposable: CompositeDisposable) {
        commentsFetchOutcome.loading(true)
        compositeDisposable.add(
                postService.getComments(postId)
                        .performOnBackOutOnMain()
                        .subscribe({ comments -> saveCommentsForPost(comments) }, { error -> handleError(error) }))
    }

    private fun saveCommentsForPost(comments: List<Comment>) {
        Completable.fromAction {
            postDb.commentDao().insertAll(comments)
        }
                .performOnBackOutOnMain()
                .subscribe()
    }

    private fun handleError(error: Throwable) {
        commentsFetchOutcome.failed(error)
    }

}