package com.karntrehan.posts.list.entity;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;

import javax.inject.Scope;

/**
 * Created by karn on 03-06-2017.
 */

public class Post {

    /*"userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita...."*/

    @SerializedName("userId")
    private int userId;

    @SerializedName("id")
    private long postId;

    @SerializedName("title")
    private String postTitle;

    @SerializedName("body")
    private String postBody;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", postBody='" + postBody + '\'' +
                '}';
    }

    ////////////////////////////////////////////////////////////////////
    //// Custom methods to handle lenght of body
    ////////////////////////////////////////////////////////////////////

    public String getFormattedPostBody() {
        if (postBody.length() <= 120)
            return postBody;
        else
            return postBody.substring(0, 118) + "....";
    }
}
