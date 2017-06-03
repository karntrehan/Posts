package com.karntrehan.posts.details.di;


import com.karntrehan.posts.details.DetailsActivity;
import com.karntrehan.posts.list.ListActivity;
import com.karntrehan.posts.list.di.ListModule;

import dagger.Subcomponent;

/**
 * Created by karn on 03-06-2017.
 */
@DetailScope
@Subcomponent(modules = DetailModule.class)
public interface DetailComponent {
    void inject(DetailsActivity detailsActivity);
}
