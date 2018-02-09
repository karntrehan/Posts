package com.karntrehan.posts.commons

import com.karntrehan.posts.commons.data.PostWithUser

object DummyData {
    fun PostWithUser(id: Int) = PostWithUser(id, "Title$id", "Body$id", "Username$id")
}