package com.karntrehan.posts.commons

import com.karntrehan.posts.core.application.CoreApp
import com.karntrehan.posts.details.di.DaggerDetailsComponent
import com.karntrehan.posts.details.di.DetailsComponent
import com.karntrehan.posts.list.di.DaggerListComponent
import com.karntrehan.posts.list.di.ListComponent
import javax.inject.Singleton

@Singleton
object PostDH {
    private var listComponent: ListComponent? = null
    private var detailsComponent: DetailsComponent? = null

    fun listComponent(): ListComponent {
        if (listComponent == null)
            listComponent = DaggerListComponent.builder().coreComponent(CoreApp.coreComponent).build()
        return listComponent as ListComponent
    }

    fun destroyListComponent() {
        listComponent = null
    }

    fun detailsComponent(): DetailsComponent {
        if (detailsComponent == null)
            detailsComponent = DaggerDetailsComponent.builder().listComponent(listComponent()).build()
        return detailsComponent as DetailsComponent
    }

    fun destroyDetailsComponent() {
        detailsComponent = null
    }
}