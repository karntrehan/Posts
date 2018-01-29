package com.karntrehan.posts.core.extensions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mpaani.core.networking.Outcome
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Created by karn on 18/1/18.
 */
fun <T> PublishSubject<T>.toLiveData(compositeDisposable: CompositeDisposable): LiveData<T> {
    val data = MutableLiveData<T>()
    compositeDisposable.add(this.subscribe({ t: T -> data.value = t }))
    return data
}

fun <T> PublishSubject<Outcome<T>>.failed(e: Throwable) {
    this.onNext(Outcome.loading(false))
    this.onNext(Outcome.failure(e))
}

fun <T> PublishSubject<Outcome<T>>.success(t: T) {
    this.onNext(Outcome.loading(false))
    this.onNext(Outcome.success(t))
}

fun <T> PublishSubject<Outcome<T>>.loading(isLoading: Boolean) {
    this.onNext(Outcome.loading(isLoading))
}