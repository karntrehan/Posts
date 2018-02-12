package com.karntrehan.posts.list.model

import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.data.local.Post
import com.karntrehan.posts.commons.data.local.User
import com.karntrehan.posts.core.extensions.*
import com.karntrehan.posts.core.networking.Scheduler
import com.mpaani.core.networking.Outcome
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject


class ListRepository(private val local: ListDataContract.Local,
                     private val remote: ListDataContract.Remote,
                     private val scheduler: Scheduler,
                     private val compositeDisposable: CompositeDisposable) : ListDataContract.Repository {

    override val postFetchOutcome: PublishSubject<Outcome<List<PostWithUser>>> = PublishSubject.create<Outcome<List<PostWithUser>>>()

    //Need to perform a remoteFetch or not?
    var remoteFetch = true

    override fun fetchPosts() {
        postFetchOutcome.loading(true)
        //Observe changes to the db
        local.getPostsWithUsers()
                .performOnBackOutOnMain(scheduler)
                .subscribe({ retailers ->
                    postFetchOutcome.success(retailers)
                    if (remoteFetch)
                        refreshPosts()
                    remoteFetch = false
                }, { error -> handleError(error) })
                .addTo(compositeDisposable)
    }

    override fun refreshPosts() {
        postFetchOutcome.loading(true)
        Flowable.zip(
                remote.getUsers(),
                remote.getPosts(),
                BiFunction<List<User>, List<Post>, Unit> { t1, t2 -> saveUsersAndPosts(t1, t2) }
        )
                .performOnBackOutOnMain(scheduler)
                .subscribe({}, { error -> handleError(error) })
                .addTo(compositeDisposable)
    }

    override fun saveUsersAndPosts(users: List<User>, posts: List<Post>) {
        local.saveUsersAndPosts(users, posts)
    }

    override fun handleError(error: Throwable) {
        postFetchOutcome.failed(error)
    }

}