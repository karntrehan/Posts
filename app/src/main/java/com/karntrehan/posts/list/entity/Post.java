package com.karntrehan.posts.list.entity;

import com.google.gson.annotations.SerializedName;
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
    private Long postId;

    @SerializedName("title")
    private String postTitle;

    @SerializedName("body")
    private String postBody;

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
    //// Custom methods to handle length of body
    ////////////////////////////////////////////////////////////////////

    public String getFormattedPostBody() {
        if (postBody.length() <= 70)
            return postBody;
        else
            return postBody.substring(0, 67) + "...";
    }

    public String getPostTitle() {
        return postTitle;
    }
}
