package com.karntrehan.posts.core.application

import android.app.Application
import com.facebook.stetho.Stetho
import com.karntrehan.posts.core.BuildConfig
import com.karntrehan.posts.core.di.AppModule
import com.karntrehan.posts.core.di.CoreComponent
import com.karntrehan.posts.core.di.DaggerCoreComponent

open class CoreApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDI()
        initStetho()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }

    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }


}