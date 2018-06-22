package com.karntrehan.posts.list.model

import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.data.local.Post
import com.karntrehan.posts.commons.data.local.User
import com.karntrehan.posts.core.extensions.*
import com.karntrehan.posts.core.networking.Scheduler
import com.karntrehan.posts.core.networking.synk.Synk
import com.karntrehan.posts.core.networking.synk.SynkKeys
import com.mpaani.core.networking.Outcome
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class ListRepository(
    private val local: ListDataContract.Local,
    private val remote: ListDataContract.Remote,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : ListDataContract.Repository {

    override val postFetchOutcome: PublishSubject<Outcome<List<PostWithUser>>> =
        PublishSubject.create<Outcome<List<PostWithUser>>>()

    override fun fetchPosts() {
        postFetchOutcome.loading(true)
        //Observe changes to the db
        local.getPostsWithUsers()
            .performOnBackOutOnMain(scheduler)
            .doAfterNext {
                if (Synk.shouldSync(SynkKeys.POSTS_HOME, 2, TimeUnit.HOURS))
                    refreshPosts()
            }
            .subscribe({ postsWithUsers ->
                postFetchOutcome.success(postsWithUsers)
            }, { error -> handleError(error) })
            .addTo(compositeDisposable)
    }

    override fun refreshPosts() {
        postFetchOutcome.loading(true)
        Single.zip(
            remote.getUsers(),
            remote.getPosts(),
            zipUsersAndPosts()
        )
            .performOnBackOutOnMain(scheduler)
            .updateSynkStatus(key = SynkKeys.POSTS_HOME)
            .subscribe({}, { error -> handleError(error) })
            .addTo(compositeDisposable)
    }

    private fun zipUsersAndPosts() =
        BiFunction<List<User>, List<Post>, Unit> { users, posts ->
            saveUsersAndPosts(users, posts)
        }

    override fun saveUsersAndPosts(users: List<User>, posts: List<Post>) {
        local.saveUsersAndPosts(users, posts)
    }

    override fun handleError(error: Throwable) {
        postFetchOutcome.failed(error)
    }

}