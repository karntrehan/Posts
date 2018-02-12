package com.karntrehan.posts.list.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.karntrehan.posts.list.model.ListDataContract

@Suppress("UNCHECKED_CAST")
class ListViewModelFactory(private val repository: ListDataContract.Repository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(repository) as T
    }
}