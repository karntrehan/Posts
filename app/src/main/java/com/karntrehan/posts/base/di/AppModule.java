package com.karntrehan.posts.base.di;

import android.content.Context;

import com.karntrehan.posts.PostApp;

import dagger.Module;
import dagger.Provides;

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
}
