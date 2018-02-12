package com.karntrehan.posts.commons.data.local

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface CommentDao {

    @Query("SELECT * from comment where postId = :postId")
    fun getForPost(postId: Int): Flowable<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(comments: List<Comment>)

    @Delete
    fun delete(comment: Comment)
}