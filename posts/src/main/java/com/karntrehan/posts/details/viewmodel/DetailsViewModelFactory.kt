package com.karntrehan.posts.details.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.karntrehan.posts.details.model.DetailsRepository

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(private val detailsRepository: DetailsRepository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(detailsRepository) as T
    }
}