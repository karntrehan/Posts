package com.karntrehan.posts.list.di;

import com.karntrehan.posts.list.ListAdapter;
import com.karntrehan.posts.list.ListContract;
import com.karntrehan.posts.list.ListModel;
import com.karntrehan.posts.list.ListPresenter;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by karn on 03-06-2017.
 */
@Module
public class ListModule {

    @ListScope
    @Provides
    ListContract.Model model(Retrofit retrofit, StorIOSQLite storIOSQLite) {
        return new ListModel(retrofit, storIOSQLite);
    }

    @ListScope
    @Provides
    ListContract.Presenter presenter(ListContract.Model model) {
        return new ListPresenter(model);
    }

    @ListScope
    @Provides
    ListAdapter listAdapter(ListContract.Presenter presenter) {
        return new ListAdapter(presenter);
    }
}
