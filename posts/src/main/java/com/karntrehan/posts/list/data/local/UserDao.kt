package com.karntrehan.posts.list.data.local

import android.arch.persistence.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)
}