package com.jason.test.newsapp.ui.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.jason.test.newsapp.databinding.FragmentNewsFilterBinding
import com.jason.test.newsapp.network.UIState
import com.jason.test.newsapp.ui.adapter.NewsResultAdapter
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsFragment : DaggerFragment() {
    private var _binding: FragmentNewsFilterBinding? = null
    private lateinit var adapter: NewsResultAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var loading = false

    @Inject
    lateinit var newsFragmentViewModel: NewsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NewsResultAdapter()
        val mLayoutManager = LinearLayoutManager(context)
        binding.newsList.layoutManager = mLayoutManager
        binding.newsList.adapter = adapter

        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    loading = false
                    binding.swipeRefresh.isRefreshing = false
                }
                is LoadState.Loading -> {
                    loading = true
                }
                is LoadState.Error -> {
                    loading = false
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }

        binding.searchButton.setOnClickListener {
            binding.inputText.text.toString().let {
                newsFragmentViewModel.getNewData(it)
            }
        }

        binding.inputText.let { inputTextView ->
            inputTextView.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    newsFragmentViewModel.getNewData(inputTextView.text.toString())
                }
                false
            })
        }

        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }

        lifecycleScope.launch {
            newsFragmentViewModel.uiState.collectLatest { uiState ->
                when (uiState) {
                    is UIState.Success -> {
                        adapter.submitData(uiState.newsResult)
                    }
                    is UIState.Error -> {
                        uiState.exception?.let { exception ->
                            Toast.makeText(
                                this@NewsFragment.activity,
                                exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        }
        newsFragmentViewModel.getNewData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}