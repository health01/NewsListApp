/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jason.test.newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.jason.test.newsapp.db.NewsResultDao
import com.jason.test.newsapp.network.UIState
import com.jason.test.newsapp.repository.NewsRepository
import com.jason.test.newsapp.ui.main.news.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class RepoViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private val repository = mock(NewsRepository::class.java)
    private var viewModel = spy(NewsViewModel(repository))
    private val newsResultDao = mock(NewsResultDao::class.java)
    val keyWord = "test"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }


    @After
    fun dropdown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testNull() {
        runBlocking {
            assertThat(viewModel.uiState, notNullValue())
            verify(repository, never()).getDetailFlow(anyString(), anyInt())
        }
    }

    @Test
    fun `retrieve successful when Repository return correct data set`() {
        runBlocking {
            `when`(repository.getDetailFlow(anyString(), anyInt())).thenReturn(flow {
                newsResultDao.getNewsByKey(
                    keyWord
                )
            })

            viewModel.getNewData(keyWord, 10)
            verify(repository).getDetailFlow(keyWord, 10)
            verify(viewModel).getNewData(keyWord, 10)
            verify(newsResultDao).getNewsByKey(keyWord)

            viewModel.setUiState(UIState.Success(PagingData.empty()))
            assertThat(viewModel.uiState.value, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(
                viewModel.uiState.value,
                CoreMatchers.instanceOf(UIState.Success::class.java)
            )
        }
    }

    @Test
    fun `retrieve error state when Repository return incorrect data set`() {
        viewModel.getNewData(keyWord, 10)

        assertThat(viewModel.uiState.value, CoreMatchers.`is`(IsNull.notNullValue()))
        assertThat(
            viewModel.uiState.value,
            CoreMatchers.instanceOf(UIState.Error::class.java)
        )
    }
}