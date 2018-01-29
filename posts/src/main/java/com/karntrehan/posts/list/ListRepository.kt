package com.karntrehan.posts.list

import com.karntrehan.posts.core.extensions.failed
import com.karntrehan.posts.core.extensions.loading
import com.karntrehan.posts.core.extensions.performOnBackOutOnMain
import com.karntrehan.posts.core.extensions.success
import com.karntrehan.posts.list.data.local.Post
import com.karntrehan.posts.list.data.local.PostDb
import com.karntrehan.posts.list.data.remote.PostService
import com.mpaani.core.networking.Outcome
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject


class ListRepository(private val postDb: PostDb, private val postService: PostService) {

    val postFetchOutcome: PublishSubject<Outcome<List<Post>>> = PublishSubject.create<Outcome<List<Post>>>()

    //Need to perform a remoteFetch or not?
    private var remoteFetch = true

    fun refreshPosts(compositeDisposable: CompositeDisposable) {
        postFetchOutcome.loading(true)
        compositeDisposable.add(postService.getPosts()
                .performOnBackOutOnMain()
                .subscribe({ retailers -> handleSuccess(retailers) }, { error -> handleError(error) }))
    }

    private fun handleError(error: Throwable) {
        postFetchOutcome.failed(error)
    }

    private fun handleSuccess(retailers: List<Post>) {
        //Insert all the remote entries into the db
        Completable.fromAction { postDb.postDao().insertAll(retailers) }
                .performOnBackOutOnMain()
                .subscribe()
    }

    fun fetchPosts(compositeDisposable: CompositeDisposable) {
        postFetchOutcome.loading(true)
        //Observe changes to the db
        compositeDisposable.add(postDb.postDao().getAll()
                .performOnBackOutOnMain()
                .subscribe({ retailers ->
                    postFetchOutcome.success(retailers)
                    if (remoteFetch)
                        refreshPosts(compositeDisposable)
                    remoteFetch = false
                }, { error -> handleError(error) })
        )
    }
}