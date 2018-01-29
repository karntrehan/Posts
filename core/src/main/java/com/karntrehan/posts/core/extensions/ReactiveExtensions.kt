package com.karntrehan.posts.core.extensions

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Completable.performOnBackOutOnMain(): Completable {
   return  this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.performOnBackOutOnMain():  Flowable<T> {
    return  this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.performOnBackOutOnMain(): Single<T> {
    return  this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
