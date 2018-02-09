package com.karntrehan.posts.core.di

import android.content.Context
import android.content.SharedPreferences
import com.karntrehan.posts.core.networking.Scheduler
import com.squareup.picasso.Picasso
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class, ImageModule::class])
interface CoreComponent {

    fun context(): Context

    fun retrofit(): Retrofit

    fun picasso(): Picasso

    fun sharedPreferences(): SharedPreferences

    fun scheduler(): Scheduler
}