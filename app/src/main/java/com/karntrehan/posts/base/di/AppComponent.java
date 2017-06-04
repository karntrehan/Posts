package com.karntrehan.posts.base.di;

import com.karntrehan.posts.details.di.DetailComponent;
import com.karntrehan.posts.details.di.DetailModule;
import com.karntrehan.posts.list.di.ListComponent;
import com.karntrehan.posts.list.di.ListModule;

import dagger.Component;

/**
 * Created by karn on 03-06-2017.
 */
@AppScope
@Component(modules = {AppModule.class,NetworkModule.class,DbModule.class,PicassoModule.class})
public interface AppComponent {
    ListComponent plus(ListModule listModule);
    DetailComponent plus(DetailModule detailModule);
}
