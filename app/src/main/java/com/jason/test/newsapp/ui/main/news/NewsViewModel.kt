package com.jason.test.newsapp.ui.main.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jason.test.newsapp.network.UIState
import com.jason.test.newsapp.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
//            private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Error(null))
    val uiState: StateFlow<UIState> = _uiState

    fun getNewData(keyWord: String = "", pageSize: Int = 10) =
        viewModelScope.launch {
            getData(keyWord, pageSize)
        }

    private suspend fun getData(keyWord: String, pageSize: Int) {
        newsRepository.getDetailFlow(keyWord, pageSize)
            .flowOn(dispatcher)
            .catch { exception ->

//                setUiState(UIState.Error(exception))
                _uiState.value = UIState.Error(exception)

            }
            .collect { list ->
//                setUiState(UIState.Success(list))
                _uiState.value = UIState.Success(list)

            }
    }

    fun setUiState(uiState: UIState) {
        _uiState.value = uiState
    }
}