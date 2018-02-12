package com.karntrehan.posts.details.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.karntrehan.posts.commons.PostDH
import com.karntrehan.posts.commons.data.local.Comment
import com.karntrehan.posts.core.extensions.toLiveData
import com.karntrehan.posts.details.model.DetailsDataContract
import com.mpaani.core.networking.Outcome
import io.reactivex.disposables.CompositeDisposable

class DetailsViewModel(private val repo: DetailsDataContract.Repository, private val compositeDisposable: CompositeDisposable) : ViewModel() {

    val commentsOutcome: LiveData<Outcome<List<Comment>>> by lazy {
        repo.commentsFetchOutcome.toLiveData(compositeDisposable)
    }

    fun loadCommentsFor(postId: Int?) {
        repo.fetchCommentsFor(postId)
    }

    fun refreshCommentsFor(postId: Int?) {
        if (postId != null)
            repo.refreshComments(postId)
    }

    override fun onCleared() {
        super.onCleared()
        //clear the disposables when the viewmodel is cleared
        compositeDisposable.clear()
        PostDH.destroyDetailsComponent()
    }
}