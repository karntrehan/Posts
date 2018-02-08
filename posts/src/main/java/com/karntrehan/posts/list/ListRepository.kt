package com.karntrehan.posts.list

import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.data.local.Post
import com.karntrehan.posts.commons.data.local.PostDb
import com.karntrehan.posts.commons.data.local.User
import com.karntrehan.posts.commons.data.remote.PostService
import com.karntrehan.posts.core.extensions.*
import com.mpaani.core.networking.Outcome
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject


class ListRepository(private val postDb: PostDb, private val postService: PostService) {

    val postFetchOutcome: PublishSubject<Outcome<List<PostWithUser>>> = PublishSubject.create<Outcome<List<PostWithUser>>>()

    //Need to perform a remoteFetch or not?
    private var remoteFetch = true

    private val TAG = "ListRepository"

    fun fetchPosts(compositeDisposable: CompositeDisposable) {
        postFetchOutcome.loading(true)
        //Observe changes to the db
        postDb.postDao().getAll()
                .performOnBackOutOnMain()
                .subscribe({ retailers ->
                    postFetchOutcome.success(retailers)
                    if (remoteFetch)
                        refreshPosts(compositeDisposable)
                    remoteFetch = false
                }, { error -> handleError(error) })
                .addTo(compositeDisposable)
    }

    fun refreshPosts(compositeDisposable: CompositeDisposable) {
        postFetchOutcome.loading(true)
        Flowable.zip(
                postService.getUsers(),
                postService.getPosts(),
                BiFunction<List<User>, List<Post>, Unit> { t1, t2 -> saveUsersAndPosts(t1, t2) }
        )
                .performOnBackOutOnMain()
                .subscribe({}, { error -> handleError(error) })
                .addTo(compositeDisposable)
    }

    private fun saveUsersAndPosts(users: List<User>, posts: List<Post>) {
        Completable.fromAction {
            postDb.userDao().insertAll(users)
            postDb.postDao().insertAll(posts)
        }
                .performOnBackOutOnMain()
                .subscribe()
    }

    private fun handleError(error: Throwable) {
        postFetchOutcome.failed(error)
    }

}