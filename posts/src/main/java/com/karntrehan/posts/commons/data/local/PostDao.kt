package com.karntrehan.posts.commons.data.local

import android.arch.persistence.room.*
import com.karntrehan.posts.commons.data.PostWithUser
import io.reactivex.Flowable


@Dao
interface PostDao {
    @Query("SELECT post.postId AS postId, post.postTitle AS postTitle ,post.postBody AS postBody, user.userName as userName FROM post, user WHERE post.userId= user.userId")
    fun getAll(): Flowable<List<PostWithUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>)

    @Delete
    fun delete(post: Post)
}