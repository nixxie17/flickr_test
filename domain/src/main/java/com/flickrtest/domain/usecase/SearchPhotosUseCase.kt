package com.flickrtest.domain.usecase

import com.flickrtest.domain.model.Photo
import com.flickrtest.domain.repository.PhotosRepository
import io.reactivex.Single

class SearchPhotosUseCase(
    private val repository: PhotosRepository
) : SingleUseCase<List<Photo>, SearchPhotosUseCase.Params>() {
    override fun buildUseCase(params: Params): Single<List<Photo>> =
        repository.searchPhotosByKeyword(params.keyword, params.page)

    data class Params(
        val keyword: String,
        val page: Int
    )
}