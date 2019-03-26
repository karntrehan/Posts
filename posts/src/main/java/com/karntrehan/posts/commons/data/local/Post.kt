package com.karntrehan.posts.commons.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = [(ForeignKey(entity = User::class, parentColumns = ["id"],
        childColumns = ["userId"], onDelete = ForeignKey.CASCADE))],
        indices = [(Index("userId"))])
data class Post(@SerializedName("userId") val userId: Int,
                @SerializedName("id") @PrimaryKey val postId: Int,
                @SerializedName("title") val postTitle: String,
                @SerializedName("body") val postBody: String)