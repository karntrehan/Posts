package com.karntrehan.posts.list.di;

import com.karntrehan.posts.list.ListContract;
import com.karntrehan.posts.list.ListModel;
import com.karntrehan.posts.list.ListPresenter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by karn on 03-06-2017.
 */
@Module
public class ListModule {

    @Provides
    ListContract.Model model(Retrofit retrofit) {
        return new ListModel(retrofit);
    }

    @Provides
    ListContract.Presenter presenter(ListContract.Model model)
    {
        return new ListPresenter(model);
    }
}
