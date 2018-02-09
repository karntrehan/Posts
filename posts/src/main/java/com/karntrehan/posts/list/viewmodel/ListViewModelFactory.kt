package com.karntrehan.posts.list.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.karntrehan.posts.list.model.ListRepository

@Suppress("UNCHECKED_CAST")
class ListViewModelFactory(private val listRepository: ListRepository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(listRepository) as T
    }
}