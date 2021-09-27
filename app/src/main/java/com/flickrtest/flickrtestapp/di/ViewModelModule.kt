package com.flickrtest.flickrtestapp.di

import com.flickrtest.flickrtestapp.searchFeature.SearchViewModel
import com.flickrtest.flickrtestapp.searchResultFeature.SearchResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel{
        SearchViewModel()
    }
    viewModel{
        SearchResultViewModel(get(), get())
    }
}