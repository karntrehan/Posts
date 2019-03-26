package com.karntrehan.posts.commons.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = [(ForeignKey(entity = Post::class, parentColumns = arrayOf("postId"),
        childColumns = arrayOf("postId"), onDelete = ForeignKey.CASCADE))],
        indices = [(Index("postId"))])
data class Comment(@SerializedName("postId") val postId: Int,
                   @SerializedName("id") @PrimaryKey val id: Int,
                   @SerializedName("name") val name: String,
                   @SerializedName("email") val email: String,
                   @SerializedName("body") val body: String)