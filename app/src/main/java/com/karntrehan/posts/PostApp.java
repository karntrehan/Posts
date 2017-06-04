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
 * The first class to run when the app is launched. Should initiate the dagger components
 * and other libraries.
 */

public class PostApp extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        initStetho();
    }

    private void initDagger() {
        //Initialize the dagger app component with the application context
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    private void initStetho() {
        //Activate stetho only if the app is in debug mode
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);
    }

    public ListComponent createListComponent() {
        return appComponent.plus(new ListModule());
    }

    public DetailComponent createDetailComponent() {
        return appComponent.plus(new DetailModule());
    }
}
