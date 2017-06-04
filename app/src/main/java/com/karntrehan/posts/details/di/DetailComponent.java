package com.karntrehan.posts.details.di;


import com.karntrehan.posts.details.DetailsActivity;

import dagger.Subcomponent;

/**
 * Created by karn on 03-06-2017.
 */
@DetailScope
@Subcomponent(modules = DetailModule.class)
public interface DetailComponent {
    void inject(DetailsActivity detailsActivity);
}
