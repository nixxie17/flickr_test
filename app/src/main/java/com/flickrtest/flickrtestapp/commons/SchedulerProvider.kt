package com.flickrtest.flickrtestapp.commons

import io.reactivex.Scheduler

interface SchedulerProvider {

    val main: Scheduler

    val computation: Scheduler

    val io: Scheduler
}