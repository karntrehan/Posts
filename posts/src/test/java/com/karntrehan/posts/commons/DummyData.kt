package com.karntrehan.posts.commons

import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.data.local.Post
import com.karntrehan.posts.commons.data.local.User

object DummyData {
    fun PostWithUser(id: Int) = PostWithUser(id, "Title$id", "Body$id", "Username$id")
    fun Post(id: Int) = Post(id, id, "title$id", "body$id")
    fun User(id: Int) = User(id,"username$id","userIdentity$id","email$id")
}