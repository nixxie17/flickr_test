package com.flickrtest.domain.usecase

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


abstract class SingleUseCase<T, in Params : Any> {

    protected abstract fun buildUseCase(params: Params): Single<T>

    fun execute(params: Params): Single<T> {
        return buildUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
    }
}