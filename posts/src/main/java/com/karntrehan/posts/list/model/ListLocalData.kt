package com.karntrehan.posts.list.model

import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.data.local.Post
import com.karntrehan.posts.commons.data.local.PostDb
import com.karntrehan.posts.commons.data.local.User
import com.karntrehan.posts.core.extensions.performOnBack
import com.karntrehan.posts.core.networking.Scheduler
import io.reactivex.Completable
import io.reactivex.Flowable

class ListLocalData(private val postDb: PostDb, private val scheduler: Scheduler) : ListDataContract.Local {

    override fun getPostsWithUsers(): Flowable<List<PostWithUser>> {
        return postDb.postDao().getAllPostsWithUser()
    }

    override fun saveUsersAndPosts(users: List<User>, posts: List<Post>) {
        Completable.fromAction {
            postDb.userDao().upsertAll(users)
            postDb.postDao().upsertAll(posts)
        }
                .performOnBack(scheduler)
                .subscribe()
    }
}