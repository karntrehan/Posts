package com.karntrehan.posts.commons.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = [(ForeignKey(entity = User::class, parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userId"), onDelete = ForeignKey.CASCADE))])
data class Post(@SerializedName("userId") val userId: Int,
                @SerializedName("id") @PrimaryKey val postId: Int,
                @SerializedName("title") val postTitle: String,
                @SerializedName("body") val postBody: String)