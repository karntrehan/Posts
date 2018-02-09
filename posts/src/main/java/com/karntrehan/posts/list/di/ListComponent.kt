package com.karntrehan.posts.list.di

import android.arch.persistence.room.Room
import android.content.Context
import com.karntrehan.posts.core.constants.Constants
import com.karntrehan.posts.core.di.CoreComponent
import com.karntrehan.posts.list.ListActivity
import com.karntrehan.posts.list.ListAdapter
import com.karntrehan.posts.list.model.ListRepository
import com.karntrehan.posts.list.viewmodel.ListViewModelFactory
import com.karntrehan.posts.commons.data.local.PostDb
import com.karntrehan.posts.commons.data.remote.PostService
import com.karntrehan.posts.core.networking.Scheduler
import com.karntrehan.posts.list.model.ListDataContract
import com.karntrehan.posts.list.model.ListLocalData
import com.karntrehan.posts.list.model.ListRemoteData
import com.squareup.picasso.Picasso
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@ListScope
@Component(dependencies = [CoreComponent::class], modules = [ListModule::class])
interface ListComponent {

    //Expose to dependent components
    fun postDb(): PostDb

    fun postService(): PostService
    fun picasso(): Picasso
    fun scheduler(): Scheduler

    fun inject(listActivity: ListActivity)
}

@Module
@ListScope
class ListModule {

    /*Adapter*/
    @Provides
    @ListScope
    fun adapter(picasso: Picasso): ListAdapter = ListAdapter(picasso)

    /*ViewModel*/
    @Provides
    @ListScope
    fun listViewModelFactory(repository: ListDataContract.Repository): ListViewModelFactory = ListViewModelFactory(repository)

    /*Repository*/
    @Provides
    @ListScope
    fun listRepo(local: ListDataContract.Local, remote: ListDataContract.Remote, scheduler: Scheduler): ListDataContract.Repository = ListRepository(local, remote, scheduler)

    @Provides
    @ListScope
    fun remoteData(postService: PostService): ListDataContract.Remote = ListRemoteData(postService)

    @Provides
    @ListScope
    fun localData(postDb: PostDb, scheduler: Scheduler): ListDataContract.Local = ListLocalData(postDb, scheduler)

    /*Parent providers to dependents*/
    @Provides
    @ListScope
    fun postDb(context: Context): PostDb = Room.databaseBuilder(context, PostDb::class.java, Constants.Posts.DB_NAME).build()

    @Provides
    @ListScope
    fun postService(retrofit: Retrofit): PostService = retrofit.create(PostService::class.java)
}