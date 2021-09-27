package com.flickrtest.flickrtestapp

import com.flickrtest.domain.model.Photo
import com.flickrtest.domain.repository.PhotosRepository
import com.flickrtest.domain.usecase.SearchPhotosUseCase
import com.flickrtest.flickrtestapp.commons.SchedulerProvider
import com.flickrtest.flickrtestapp.searchResultFeature.SearchResultViewModel
import com.flickrtest.flickrtestapp.utils.observeOnce
import com.flickrtest.flickrtestapp.utils.resetLiveData
import com.flickrtest.flickrtestapp.utils.setupLiveData
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import strikt.api.expectThat
import strikt.assertions.isEqualTo

const val ID = "1234"
const val URL = "https://example.com"
const val TITLE = "image"

val response = Single.fromObservable(
    Observable.fromIterable(mutableListOf<Photo>().apply {
        for (i in 0 until 5) {
            add(
                Photo(
                    id = ID,
                    url = URL,
                    title = TITLE
                )
            )
        }
    })
        .toList()
        .toObservable()
)


class SearchResultViewModelSpec : DescribeSpec({
    val photosRepo = mockk<PhotosRepository>()
    val searchPhotosUseCase = SearchPhotosUseCase(photosRepo)
    val schedulerProvider = object : SchedulerProvider {
        override val main: Scheduler = Schedulers.trampoline()
        override val computation: Scheduler = Schedulers.trampoline()
        override val io: Scheduler = Schedulers.trampoline()
    }

    lateinit var viewModel: SearchResultViewModel

    beforeEach {
        setupLiveData()
        viewModel = SearchResultViewModel(
            schedulerProvider,
            searchPhotosUseCase,
        )
    }

    afterEach {
        resetLiveData()
        clearMocks(photosRepo)
    }

    describe("loading photos from Flickr API by a keyword input") {
        val keyword = "cat"

        it("viewModel emits expected live data value") {
            every {
                photosRepo.searchPhotosByKeyword(keyword, 1)
            } answers { response }

            viewModel.loadData(keyword)
            verify { photosRepo.searchPhotosByKeyword(any(), any()) }
            viewModel.photosLiveData.observeOnce { listPhotos ->
                expectThat(listPhotos.size).isEqualTo(5)

                val photo = listPhotos[0]
                expectThat(photo.id).isEqualTo(ID)
                expectThat(photo.url).isEqualTo(URL)
                expectThat(photo.title).isEqualTo(TITLE)
            }
        }

        it("usecase observable object correct behavior") {
            val result = searchPhotosUseCase.execute(SearchPhotosUseCase.Params(keyword, 1))
            val testObserver = TestObserver<List<Photo>>()
            result.subscribe(testObserver)
            testObserver.assertComplete()
            testObserver.assertNoErrors()
            testObserver.assertValueCount(1)
        }
    }
})