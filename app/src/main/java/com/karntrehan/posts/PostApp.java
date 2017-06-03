package com.karntrehan.posts;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.karntrehan.posts.base.di.AppComponent;
import com.karntrehan.posts.base.di.AppModule;
import com.karntrehan.posts.base.di.DaggerAppComponent;
import com.karntrehan.posts.details.di.DetailComponent;
import com.karntrehan.posts.details.di.DetailModule;
import com.karntrehan.posts.list.di.ListComponent;
import com.karntrehan.posts.list.di.ListModule;


/**
 * Created by karn on 03-06-2017.
 */

public class PostApp extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initORM();
        initDagger();
        initStetho();
    }

    private void initORM() {
        //TODO
    }

    private void initStetho() {
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);
    }

    private void initDagger() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ListComponent createListComponent() {
        return appComponent.plus(new ListModule());
    }

    public DetailComponent createDetailComponent() {
        return appComponent.plus(new DetailModule());
    }
}
