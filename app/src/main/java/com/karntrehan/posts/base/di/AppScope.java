package com.karntrehan.posts.base.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by karn on 03-06-2017.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@interface AppScope {
}
