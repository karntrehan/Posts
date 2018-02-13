package com.karntrehan.posts.details.model

import com.karntrehan.posts.commons.data.local.Comment
import com.karntrehan.posts.core.extensions.*
import com.karntrehan.posts.core.networking.Scheduler
import com.karntrehan.posts.details.exceptions.DetailsExceptions
import com.mpaani.core.networking.Outcome
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class DetailsRepository(
    private val local: DetailsDataContract.Local,
    private val remote: DetailsDataContract.Remote,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : DetailsDataContract.Repository {

    override val commentsFetchOutcome: PublishSubject<Outcome<List<Comment>>> =
        PublishSubject.create<Outcome<List<Comment>>>()

    var remoteFetch = true

    override fun fetchCommentsFor(postId: Int?) {
        if (postId == null)
            return

        commentsFetchOutcome.loading(true)
        local.getCommentsForPost(postId)
            .performOnBackOutOnMain(scheduler)
            .subscribe({ retailers ->
                commentsFetchOutcome.success(retailers)
                if (remoteFetch)
                    refreshComments(postId)
                remoteFetch = false
            }, { error -> handleError(error) })
            .addTo(compositeDisposable)
    }

    override fun refreshComments(postId: Int) {
        commentsFetchOutcome.loading(true)
        remote.getCommentsForPost(postId)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                { comments -> saveCommentsForPost(comments) },
                { error -> handleError(error) })
            .addTo(compositeDisposable)
    }

    override fun saveCommentsForPost(comments: List<Comment>) {
        if (comments.isNotEmpty()) {
            local.saveComments(comments)
        } else
            commentsFetchOutcome.failed(DetailsExceptions.NoCommentsException())
    }

    override fun handleError(error: Throwable) {
        commentsFetchOutcome.failed(error)
    }
}