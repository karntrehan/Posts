package com.karntrehan.posts.details.entity;

import com.google.gson.annotations.SerializedName;
import com.karntrehan.posts.list.entity.Post;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by karn on 03-06-2017.
 */
@StorIOSQLiteType(table = Comment.TABLE_NAME)
public class Comment {
    public static final String TABLE_NAME = "comments";
    /*
    * "postId": 1,
    "id": 1,
    "name": "id labore ex et quam laborum",
    "email": "Eliseo@gardner.biz",
    "body": "laudantium enim quasi est quidem magnam voluptate ..."*/

    @StorIOSQLiteColumn(name = "post_id")
    @SerializedName("postId")
    long postId;

    @StorIOSQLiteColumn(name = "comment_id", key = true)
    @SerializedName("id")
    long commentId;

    @StorIOSQLiteColumn(name = "name")
    @SerializedName("name")
    String name;

    @StorIOSQLiteColumn(name = "email")
    @SerializedName("email")
    String email;

    @StorIOSQLiteColumn(name = "body")
    @SerializedName("body")
    String body;

    public Comment() {
    }

    public static String createTableQuery() {
        return "CREATE TABLE " + Comment.TABLE_NAME + " ("
                + "comment_id INTEGER NOT NULL PRIMARY KEY, "
                + "post_id INTEGER NOT NULL , "
                + " name TEXT NOT NULL, "
                + " email TEXT NOT NULL,"
                + " body TEXT NOT NULL "
                + ");";
    }

    @Override
    public String toString() {
        return "Comment{" +
                "postId=" + postId +
                ", commentId=" + commentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
