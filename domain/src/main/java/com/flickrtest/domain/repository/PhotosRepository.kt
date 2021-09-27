package com.flickrtest.domain.repository

import com.flickrtest.domain.model.Photo
import io.reactivex.Single

interface PhotosRepository {
     fun searchPhotosByKeyword(keyword: String, page: Int): Single<List<Photo>>
}