package com.flickrtest.flickrtestapp.searchResultFeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flickrtest.flickrtestapp.commons.EndlessRecyclerViewScrollListener
import com.flickrtest.flickrtestapp.commons.hide
import com.flickrtest.flickrtestapp.commons.show
import com.flickrtest.flickrtestapp.commons.showSnackbar
import com.flickrtest.flickrtestapp.databinding.SearchResultFragmentBinding
import com.flickrtest.flickrtestapp.searchResultFeature.adapter.PhotosAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchResultFragment : Fragment() {

    private val viewModel: SearchResultViewModel by viewModel()
    private var binding: SearchResultFragmentBinding? = null
    private val args: SearchResultFragmentArgs by navArgs()

    private val photosAdapter = PhotosAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userVisibleHint = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchResultFragmentBinding.inflate(inflater, container, false)
        binding?.rvPhotos?.let { recyclerView ->
            recyclerView.adapter = photosAdapter
            recyclerView.layoutManager = GridLayoutManager(activity, 3)
        }
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (!isVisibleToUser) {
            viewModel.loadData(args.keyword)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setListeners() {
        viewModel.photosLiveData.observe(viewLifecycleOwner, { listPhotos ->
            binding?.progressBar?.hide()
            with(photosAdapter) {
                photos.addAll(listPhotos)
                notifyDataSetChanged()
            }
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, { error ->
            activity?.showSnackbar(this.requireView(), error, true)
        })

        binding?.rvPhotos?.apply {
            val scrollListener =
                object : EndlessRecyclerViewScrollListener(layoutManager as GridLayoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        binding?.progressBar?.show()
                        viewModel.loadMore()
                    }
                }
            addOnScrollListener(scrollListener)
        }
    }
}
