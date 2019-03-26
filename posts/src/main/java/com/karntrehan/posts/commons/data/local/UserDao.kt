package com.karntrehan.posts.commons.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(users: List<User>)

    @Query("SELECT * FROM user")
    fun getAll(): Flowable<List<User>>
}