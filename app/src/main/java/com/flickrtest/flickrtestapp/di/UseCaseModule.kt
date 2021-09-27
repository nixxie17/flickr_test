package com.flickrtest.flickrtestapp.di

import com.flickrtest.domain.repository.PhotosRepository
import com.flickrtest.domain.usecase.SearchPhotosUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { provideSearchPhotosByKeywordUseCase(get()) }
}

fun provideSearchPhotosByKeywordUseCase(photosRepository: PhotosRepository) =
    SearchPhotosUseCase(photosRepository)