package com.karntrehan.posts.commons.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(indices = [(Index("id"))])
data class User(@SerializedName("id") @PrimaryKey val id: Int,
                @SerializedName("name") val userName: String,
                @SerializedName("username") val userIdentity: String,
                @SerializedName("email") val userEmail: String)