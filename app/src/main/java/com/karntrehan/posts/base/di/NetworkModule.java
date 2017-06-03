package com.karntrehan.posts.base.di;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.karntrehan.posts.BuildConfig;
import com.karntrehan.posts.PostApp;
import com.karntrehan.posts.base.Constants;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karn on 03-06-2017.
 */

@Module(includes = AppModule.class)
public class NetworkModule {

    @AppScope
    @Provides
    public Retrofit retrofit(GsonConverterFactory gsonConverterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(Constants.API_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build();
    }

    @AppScope
    @Provides
    GsonConverterFactory gsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @AppScope
    @Provides
    OkHttpClient okHttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            client.addNetworkInterceptor(new StethoInterceptor());
        }
        return client.build();
    }

    @AppScope
    @Provides
    Cache cache(Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(context.getCacheDir(), cacheSize);
    }
}
