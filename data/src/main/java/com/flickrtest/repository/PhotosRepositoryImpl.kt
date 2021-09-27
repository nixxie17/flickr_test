package com.flickrtest.repository

import com.flickrtest.dataprovider.FlickrApi
import com.flickrtest.domain.model.Photo
import com.flickrtest.domain.repository.PhotosRepository
import com.flickrtest.mapper.toDomain
import io.reactivex.Single

class PhotosRepositoryImpl(
    private val apiDataProvider: FlickrApi
) : PhotosRepository {
    override fun searchPhotosByKeyword(keyword: String, page: Int): Single<List<Photo>> =
        apiDataProvider.searchPhotosByKeyword(keyword, page).map { it.toDomain() }
}