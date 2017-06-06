package com.karntrehan.posts.base.di;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by karn on 04-06-2017.
 */
@Module(includes = NetworkModule.class)
public class PicassoModule {

    @Provides
    @AppScope
    Picasso picasso(Context context, OkHttpClient okHttpClient)
    {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

}
