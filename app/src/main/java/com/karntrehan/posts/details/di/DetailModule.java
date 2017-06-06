package com.karntrehan.posts.details.di;

import com.karntrehan.posts.details.DetailsContract;
import com.karntrehan.posts.details.DetailsModel;
import com.karntrehan.posts.details.DetailsPresenter;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by karn on 03-06-2017.
 */

@Module
public class DetailModule {

    @Provides
    @DetailScope
    DetailsContract.Model model(Retrofit retrofit, StorIOSQLite storIOSQLite) {
        return new DetailsModel(retrofit, storIOSQLite);
    }

    @Provides
    @DetailScope
    DetailsContract.Presenter presenter(DetailsContract.Model model) {
        return new DetailsPresenter(model);
    }


}
