package com.karntrehan.posts.list.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.karntrehan.posts.core.extensions.toLiveData
import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.PostDH
import com.karntrehan.posts.list.model.ListDataContract
import com.karntrehan.posts.list.model.ListRepository
import com.mpaani.core.networking.Outcome
import io.reactivex.disposables.CompositeDisposable

class ListViewModel(private val repo: ListDataContract.Repository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val postsOutcome: LiveData<Outcome<List<PostWithUser>>> by lazy {
        //Convert publish subject to livedata
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
        //clear the disposables when the viewmodel is cleared
        compositeDisposable.clear()
        PostDH.destroyListComponent()
    }
}