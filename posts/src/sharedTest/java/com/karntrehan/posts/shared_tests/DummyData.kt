package com.karntrehan.posts.commons

import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.data.local.Post
import com.karntrehan.posts.commons.data.local.User

/*http://blog.danlew.net/2015/11/02/sharing-code-between-unit-tests-and-instrumentation-tests-on-android*/

object DummyData {
    fun PostWithUser(id: Int) = PostWithUser(id, "title$id", "body$id", "username$id")
    fun Post(id: Int) = Post(id, id, "title$id", "body$id")
    fun User(id: Int) = User(id, "username$id", "userIdentity$id", "email$id")
}