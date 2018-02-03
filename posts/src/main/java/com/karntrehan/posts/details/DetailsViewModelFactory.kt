package com.karntrehan.posts.details

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.karntrehan.posts.list.ListRepository
import com.karntrehan.posts.list.ListViewModel

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(private val detailsRepository: DetailsRepository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(detailsRepository) as T
    }
}