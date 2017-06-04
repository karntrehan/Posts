package com.karntrehan.posts.base.di;

import android.content.Context;

import com.karntrehan.posts.base.db.DbOpenHelper;
import com.karntrehan.posts.details.entity.Comment;
import com.karntrehan.posts.details.entity.CommentSQLiteTypeMapping;
import com.karntrehan.posts.details.entity.User;
import com.karntrehan.posts.details.entity.UserSQLiteTypeMapping;
import com.karntrehan.posts.list.entity.Post;
import com.karntrehan.posts.list.entity.PostSQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import dagger.Module;
import dagger.Provides;

/**
 * Created by karn on 04-06-2017.
 */

@Module(includes = AppModule.class)
public class DbModule {

    //Provices the StorIOSqlite instance used by StorIo to perform actions on the underlaying
    // SQLite database
    @Provides
    @AppScope
    StorIOSQLite storIOSQLite(Context context) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(new DbOpenHelper(context))
                .addTypeMapping(Post.class, new PostSQLiteTypeMapping())
                .addTypeMapping(Comment.class, new CommentSQLiteTypeMapping())
                .addTypeMapping(User.class, new UserSQLiteTypeMapping())
                .build();
    }

}
