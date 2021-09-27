package com.flickrtest.flickrtestapp.di

import com.flickrtest.domain.repository.PhotosRepository
import com.flickrtest.repository.PhotosRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single<PhotosRepository> { PhotosRepositoryImpl(get()) }
}