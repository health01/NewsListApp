package com.jason.test.newsapp.network

import androidx.paging.PagingData
import com.jason.test.newsapp.data.NewsResult


sealed class UIState {
    data class Error(var exception: Throwable? = NullPointerException()) : UIState()
    data class Success(var newsResult: PagingData<NewsResult>) : UIState()
}
