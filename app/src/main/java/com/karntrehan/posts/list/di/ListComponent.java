package com.karntrehan.posts.list.di;


import com.karntrehan.posts.list.ListActivity;

import dagger.Subcomponent;

import static android.R.attr.mode;

/**
 * Created by karn on 03-06-2017.
 */
@ListScope
@Subcomponent(modules = ListModule.class)
public interface ListComponent {
    void inject(ListActivity listActivity);
}
