package com.karntrehan.posts.list.data

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.karntrehan.posts.list.ListRepository
import com.karntrehan.posts.list.ListViewModel

@Suppress("UNCHECKED_CAST")
class ListViewModelFactory(private val listRepository: ListRepository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(listRepository) as T
    }
}