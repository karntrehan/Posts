package com.karntrehan.posts.base.di;

import android.app.Application;
import android.content.Context;

import com.karntrehan.posts.PostApp;
import com.karntrehan.posts.base.db.DbOpenHelper;
import com.karntrehan.posts.details.entity.Comment;
import com.karntrehan.posts.details.entity.CommentSQLiteTypeMapping;
import com.karntrehan.posts.list.entity.Post;
import com.karntrehan.posts.list.entity.PostSQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import dagger.Module;
import dagger.Provides;

import static java.security.AccessController.getContext;

/**
 * Created by karn on 03-06-2017.
 */
@Module
public class AppModule {

    private final PostApp postApp;

    public AppModule(PostApp postApp) {
        this.postApp = postApp;
    }

    @Provides
    @AppScope
    Context application() {
        return postApp.getApplicationContext();
    }

    @Provides
    @AppScope
    StorIOSQLite storIOSQLite() {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(new DbOpenHelper(postApp))
                .addTypeMapping(Post.class, new PostSQLiteTypeMapping())
                .addTypeMapping(Comment.class, new CommentSQLiteTypeMapping())
                .build();
    }
}
