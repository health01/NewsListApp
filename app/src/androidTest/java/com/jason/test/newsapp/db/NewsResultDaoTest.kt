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

package com.jason.test.newsapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jason.test.newsapp.TestHelper
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern

@RunWith(AndroidJUnit4::class)
class NewsResultDaoTest : DbTest() {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val apiCall = TestHelper.getNewsObject()

    @Test
    fun insertAndLoadWithKeyboard() {
        runBlocking {
            db.newsResultDao().insertAll(apiCall.response.results)
            val loaded = db.newsResultDao().getNewsListByKey("football")


            loaded.forEach {
                val includedKeyWord =
                    Pattern.compile(Pattern.quote("football"), Pattern.CASE_INSENSITIVE)
                        .matcher(it.webTitle).find();
                assertTrue(includedKeyWord)

            }
        }
    }

    @Test
    fun insertAndDeleteWithKeyboard() {
        runBlocking {
            db.newsResultDao().insertAll(apiCall.response.results)
            db.newsResultDao().deleteByKeyWord("football")
            val loaded = db.newsResultDao().getNewsListByKey("football")
            MatcherAssert.assertThat(loaded, CoreMatchers.`is`(arrayListOf()))

        }
    }
}


