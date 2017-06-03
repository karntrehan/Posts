package com.karntrehan.posts.base.di;

import android.content.Context;

import com.karntrehan.posts.PostApp;
import com.karntrehan.posts.details.di.DetailComponent;
import com.karntrehan.posts.details.di.DetailModule;
import com.karntrehan.posts.list.ListModel;
import com.karntrehan.posts.list.di.ListComponent;
import com.karntrehan.posts.list.di.ListModule;

import java.util.List;

import dagger.Component;

/**
 * Created by karn on 03-06-2017.
 */
@AppScope
@Component(modules = {AppModule.class,NetworkModule.class})
public interface AppComponent {
    ListComponent plus(ListModule listModule);
    DetailComponent plus(DetailModule detailModule);
}
