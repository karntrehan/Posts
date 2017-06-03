package com.karntrehan.posts.list.entity;

import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by karn on 03-06-2017.
 */

@StorIOSQLiteType(table = Post.TABLE_NAME)
public class Post {

    public static final String TABLE_NAME = "posts";

    /*"userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita...."*/

    @StorIOSQLiteColumn(name = "user_id")
    @SerializedName("userId")
    int userId;

    @StorIOSQLiteColumn(name = "post_id", key = true)
    @SerializedName("id")
    Long postId;

    @StorIOSQLiteColumn(name = "post_title")
    @SerializedName("title")
    String postTitle;

    @StorIOSQLiteColumn(name = "post_body")
    @SerializedName("body")
    String postBody;

    Post() {
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

    public static String createTableQuery() {
        return "CREATE TABLE " + Post.TABLE_NAME + " ("
                + "post_id INTEGER NOT NULL PRIMARY KEY, "
                + " post_title TEXT NOT NULL, "
                + " post_body TEXT NOT NULL,"
                + " user_id INTEGER NOT NULL "
                + ");";
    }
}
