package com.karntrehan.posts.list.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Post(@SerializedName("userId") val userId: Int,
                @SerializedName("id") @PrimaryKey val postId: Int,
                @SerializedName("title") val postTitle: String,
                @SerializedName("body") val postBody: String) {


    fun getFormattedPostBody(): String {
        return if (postBody.length <= 70)
            postBody
        else
            postBody.substring(0, 67) + "..."
    }
}