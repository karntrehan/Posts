package com.karntrehan.posts.details.di

import com.karntrehan.posts.commons.data.local.PostDb
import com.karntrehan.posts.commons.data.remote.PostService
import com.karntrehan.posts.details.DetailsActivity
import com.karntrehan.posts.details.DetailsAdapter
import com.karntrehan.posts.details.DetailsRepository
import com.karntrehan.posts.details.DetailsViewModelFactory
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
    fun detailsViewModelFactory(detailsRepository: DetailsRepository): DetailsViewModelFactory {
        return DetailsViewModelFactory(detailsRepository)
    }

    /*Repository*/
    @Provides
    @DetailsScope
    fun detailsRepo(postDb: PostDb, postService: PostService): DetailsRepository = DetailsRepository(postDb, postService)
}