package com.flickrtest.flickrtestapp.di

import com.flickrtest.flickrtestapp.commons.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module

val reactiveModule = module {

    single { provideSchedulerProvider() }
}

fun provideSchedulerProvider(): SchedulerProvider = object : SchedulerProvider {
    override val main: Scheduler = AndroidSchedulers.mainThread()
    override val computation: Scheduler = Schedulers.computation()
    override val io: Scheduler = Schedulers.io()
}