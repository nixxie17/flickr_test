package com.flickrtest.flickrtestapp

import android.app.Application
import com.flickrtest.flickrtestapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlickrTestApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FlickrTestApplication)
            modules(
                networkModule,
                repositoriesModule,
                useCaseModule,
                reactiveModule,
                viewModelsModule
            )
        }
    }
}