package com.flickrtest.flickrtestapp.searchFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.flickrtest.flickrtestapp.commons.hideKeyboard
import com.flickrtest.flickrtestapp.databinding.SearchFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    private var binding: SearchFragmentBinding? = null
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        binding?.editText?.setText(viewModel.searchPhotosText)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setListeners() {

        binding?.main?.setOnClickListener {
            hideKeyboard()
        }

        binding?.btnSearch?.setOnClickListener {
            val keyword = binding?.editText?.text.toString()
            if (keyword.isNotBlank()) {
                val action =
                    SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(keyword)
                findNavController().navigate(action)
            }
        }

        binding?.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchPhotosText = s.toString()
            }
        })
    }
}