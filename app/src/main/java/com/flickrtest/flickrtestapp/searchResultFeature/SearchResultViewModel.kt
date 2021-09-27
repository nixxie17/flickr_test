package com.flickrtest.flickrtestapp.searchResultFeature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flickrtest.domain.model.Photo
import com.flickrtest.domain.usecase.SearchPhotosUseCase
import com.flickrtest.flickrtestapp.commons.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

const val errorMessagePrefix = "Error occurred: "

class SearchResultViewModel(
    private val schedulersProvider: SchedulerProvider,
    private val searchPhotosUseCase: SearchPhotosUseCase
) : ViewModel() {

    private val _photosLiveData = MutableLiveData<List<Photo>>()
    val photosLiveData: LiveData<List<Photo>> = _photosLiveData
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData
    private val disposables = CompositeDisposable()

    private var page = 1
    private var keyword = ""

    fun loadData(keyword: String) {
        this.keyword = keyword
        fetchDataFromApi()
        page++
    }

    fun loadMore() {
        if (keyword.isNotBlank()) {
            fetchDataFromApi()
            page++
        }
    }

    private fun fetchDataFromApi() {
        disposables.add(searchPhotosUseCase
            .execute(SearchPhotosUseCase.Params(keyword, page))
            .subscribeOn(schedulersProvider.io)
            .observeOn(schedulersProvider.main)
            .doOnError {
                _errorLiveData.value = errorMessagePrefix + it.localizedMessage
            }
            .subscribe { listPhotos -> _photosLiveData.value = listPhotos }
        )
    }

    override fun onCleared() {
       disposables.dispose()
    }
}