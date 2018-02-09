package com.karntrehan.posts.details.di

import com.karntrehan.posts.commons.data.local.PostDb
import com.karntrehan.posts.commons.data.remote.PostService
import com.karntrehan.posts.core.di.CoreComponent
import com.karntrehan.posts.core.networking.Scheduler
import com.karntrehan.posts.details.DetailsActivity
import com.karntrehan.posts.details.DetailsAdapter
import com.karntrehan.posts.details.model.DetailsDataContract
import com.karntrehan.posts.details.model.DetailsLocalData
import com.karntrehan.posts.details.model.DetailsRemoteData
import com.karntrehan.posts.details.model.DetailsRepository
import com.karntrehan.posts.details.viewmodel.DetailsViewModelFactory
import com.karntrehan.posts.list.di.ListComponent
import dagger.Component
import dagger.Module
import dagger.Provides

@DetailsScope
@Component(dependencies = [ListComponent::class], modules = [DetailsModule::class])
interface DetailsComponent {
    fun inject(detailsActivity: DetailsActivity)
}

@Module
class DetailsModule {

    /*Adapter*/
    @Provides
    @DetailsScope
    fun adapter(): DetailsAdapter {
        return DetailsAdapter()
    }

    /*ViewModel*/
    @Provides
    @DetailsScope
    fun detailsViewModelFactory(repo: DetailsDataContract.Repository): DetailsViewModelFactory {
        return DetailsViewModelFactory(repo)
    }

    /*Repository*/
    @Provides
    @DetailsScope
    fun detailsRepo(local: DetailsDataContract.Local, remote: DetailsDataContract.Remote, scheduler: Scheduler)
            : DetailsDataContract.Repository = DetailsRepository(local, remote, scheduler)

    @Provides
    @DetailsScope
    fun remoteData(postService: PostService): DetailsDataContract.Remote = DetailsRemoteData(postService)

    @Provides
    @DetailsScope
    fun localData(postDb: PostDb, scheduler: Scheduler): DetailsDataContract.Local = DetailsLocalData(postDb, scheduler)
}