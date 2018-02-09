package com.karntrehan.posts.commons.data.local

import android.arch.persistence.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(users: List<User>)
}