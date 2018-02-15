package com.karntrehan.posts.commons.testing

import android.support.annotation.VisibleForTesting
import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.data.local.Comment
import com.karntrehan.posts.commons.data.local.Post
import com.karntrehan.posts.commons.data.local.User

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DummyData {
    fun User(id: Int) = User(id, "username$id", "userIdentity$id", "email$id")
    fun Post(userId: Int, id: Int) = Post(userId, id, "title$id", "body$id")
    fun PostWithUser(id: Int) = PostWithUser(id, "title$id", "body$id", "username$id")
    fun Comment(postId: Int, id: Int) = Comment(postId,id,"name$id","email$id","body$id")
}