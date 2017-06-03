package com.karntrehan.posts.base.db;

import android.database.sqlite.SQLiteOpenHelper;

import com.karntrehan.posts.PostApp;
import com.karntrehan.posts.list.entity.Post;

/**
 * Created by karn on 03-06-2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

/**
 * Created by karan on 26/1/17.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    public DbOpenHelper(@NonNull Context context) {
        super(context, "posts_db", null, 1);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(Post.createTableQuery());
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // no impl
    }
}
