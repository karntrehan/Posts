package com.karntrehan.posts.commons.data.local

import android.arch.persistence.room.*
import com.karntrehan.posts.commons.data.PostWithUser
import io.reactivex.Flowable


@Dao
interface PostDao {
    @Query("SELECT post.postId AS postId, post.postTitle AS postTitle ,post.postBody AS postBody, user.userName as userName FROM post, user WHERE post.userId= user.id")
    fun getAllPostsWithUser(): Flowable<List<PostWithUser>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(posts: List<Post>)

    @Delete
    fun delete(post: Post)

    @Query("SELECT * FROM post")
    fun getAll(): Flowable<List<Post>>
}