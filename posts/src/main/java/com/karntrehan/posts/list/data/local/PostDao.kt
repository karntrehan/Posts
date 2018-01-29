package com.karntrehan.posts.list.data.local

import android.arch.persistence.room.*
import io.reactivex.Flowable


@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getAll(): Flowable<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>)

    @Delete
    fun delete(post: Post)

}