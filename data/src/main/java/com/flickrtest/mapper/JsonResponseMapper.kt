package com.flickrtest.mapper

import com.flickrtest.domain.model.Photo
import com.flickrtest.response.SearchPhotosByKeywordResponse

fun SearchPhotosByKeywordResponse.toDomain(): List<Photo> =
    photos.photo.map { apiPhoto ->
        Photo(
            id = apiPhoto.id,
            url = "https://farm${apiPhoto.farm}.staticflickr.com/${apiPhoto.server}/${apiPhoto.id}_${apiPhoto.secret}.jpg",
            title = apiPhoto.title
        )
    }
