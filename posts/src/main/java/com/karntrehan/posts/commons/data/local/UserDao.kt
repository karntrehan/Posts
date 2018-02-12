package com.karntrehan.posts.commons.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(users: List<User>)

    @Query("SELECT * FROM user")
    fun getAll(): Flowable<List<User>>
}