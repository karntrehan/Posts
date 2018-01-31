package com.karntrehan.posts.list.data

import android.arch.persistence.room.PrimaryKey

data class PostWithUser(@PrimaryKey val postId: Int,
                        val postTitle: String,
                        val postBody: String,
                        val userName: String) {

    fun getFormattedPostBody(): String {
        return if (postBody.length <= 70)
            postBody
        else
            postBody.substring(0, 67) + "..."
    }

    fun getAvatarPhoto() = "https://api.adorable.io/avatars/64/$userName.png"
}