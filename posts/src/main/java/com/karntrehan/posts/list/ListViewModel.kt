package com.karntrehan.posts.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.karntrehan.posts.core.extensions.toLiveData
import com.karntrehan.posts.list.data.local.Post
import com.mpaani.core.networking.Outcome
import io.reactivex.disposables.CompositeDisposable

class ListViewModel(private val repo: ListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val postsOutcome: LiveData<Outcome<List<Post>>> by lazy {
        repo.postFetchOutcome.toLiveData(compositeDisposable)
    }

    fun refreshPosts() {
        repo.refreshPosts(compositeDisposable)
    }

    fun getPosts() {
        if (postsOutcome.value == null)
            repo.fetchPosts(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}