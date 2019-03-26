package com.karntrehan.posts.commons.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Post::class, User::class, Comment::class], version = 1,exportSchema = false)
abstract class PostDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao
}
