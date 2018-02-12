package com.karntrehan.posts.details.model

import com.karntrehan.posts.commons.data.local.Comment
import com.mpaani.core.networking.Outcome
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

interface DetailsDataContract {
    interface Repository {
        val commentsFetchOutcome: PublishSubject<Outcome<List<Comment>>>
        fun fetchCommentsFor(postId: Int?, compositeDisposable: CompositeDisposable)
        fun refreshComments(postId: Int, compositeDisposable: CompositeDisposable)
        fun saveCommentsForPost(comments: List<Comment>)
        fun handleError(error: Throwable)
    }

    interface Local {
        fun getCommentsForPost(postId: Int): Flowable<List<Comment>>
        fun saveComments(comments: List<Comment>)
    }

    interface Remote {
        fun getCommentsForPost(postId: Int): Flowable<List<Comment>>
    }
}