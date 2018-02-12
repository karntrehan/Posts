package com.karntrehan.posts.list.model

import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.data.local.Post
import com.karntrehan.posts.commons.data.local.User
import com.mpaani.core.networking.Outcome
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

interface ListDataContract {
    interface Repository {
        val postFetchOutcome: PublishSubject<Outcome<List<PostWithUser>>>
        fun fetchPosts()
        fun refreshPosts()
        fun saveUsersAndPosts(users: List<User>, posts: List<Post>)
        fun handleError(error: Throwable)
    }

    interface Local {
        fun getPostsWithUsers(): Flowable<List<PostWithUser>>
        fun saveUsersAndPosts(users: List<User>, posts: List<Post>)
    }

    interface Remote {
        fun getUsers(): Flowable<List<User>>
        fun getPosts(): Flowable<List<Post>>
    }
}