package com.flickrtest.dataprovider

import com.flickrtest.response.SearchPhotosByKeywordResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("?method=flickr.photos.search")
    fun searchPhotosByKeyword(
        @Query(value = "text") keyword: String,
        @Query(value = "page") page: Int
    ): Single<SearchPhotosByKeywordResponse>
}