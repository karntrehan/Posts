package com.karntrehan.posts.list.di

import com.karntrehan.posts.core.application.CoreApp
import javax.inject.Singleton

@Singleton
object ListDH {
    private var component: ListComponent? = null

    fun component(): ListComponent {
        if (component == null)
            component = DaggerListComponent.builder().coreComponent(CoreApp.coreComponent).build()
        return component as ListComponent
    }

    fun destroy() {
        component = null
    }
}