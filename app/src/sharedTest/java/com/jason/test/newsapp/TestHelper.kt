package com.jason.test.newsapp

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.jason.test.newsapp.data.News
import com.jason.test.newsapp.data.NewsResult
import com.jason.test.newsapp.network.NewsService
import okio.Okio
import retrofit2.HttpException
import java.io.IOException

object TestHelper {
    fun getJsonString(fileName: String = "news.json"): String {
        val classloader = javaClass.classLoader
        val inputStream = classloader.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        return source.readString(Charsets.UTF_8)
    }

    fun getNewsObject(): News {
        val source = TestHelper.getJsonString()
        val gson = Gson()
        return gson.fromJson(source, News::class.java)
    }

    /**
     * Sample page-keyed PagingSource, which uses Int page number to load pages.
     *
     * Loads Items from network requests via Retrofit to a backend service.
     *
     * Note that the key type is Int, since we're using page number to load a page.
     */
    class MyPagingSource(
        val myBackend: NewsService,
        val keyWord :String
    ) : PagingSource<Int, NewsResult>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsResult> {

            // Retrofit calls that return the body type throw either IOException for network
            // failures, or HttpException for any non-2xx HTTP status codes. This code reports all
            // errors to the UI, but you can inspect/wrap the exceptions to provide more context.
            return try {
                // Key may be null during a refresh, if no explicit key is passed into Pager
                // construction. Use 0 as default, because our API is indexed started at index 0
                val pageNumber = params.key ?: 0

                // Suspending network load via Retrofit. This doesn't need to be wrapped in a
                // withContext(Dispatcher.IO) { ... } block since Retrofit's Coroutine
                // CallAdapter dispatches on a worker thread.
                val json = myBackend.search(pageNumber,keyWord)

                // Since 0 is the lowest page number, return null to signify no more pages should
                // be loaded before it.
                val prevKey = if (pageNumber > 0) pageNumber - 1 else null

                // This API defines that it's out of data when a page returns empty. When out of
                // data, we return `null` to signify no more pages should be loaded
                val nextKey = if (json.response.results.isNotEmpty()) pageNumber + 1 else null
                LoadResult.Page(
                    data = json.response.results,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } catch (e: IOException) {
                LoadResult.Error(e)
            } catch (e: HttpException) {
                LoadResult.Error(e)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, NewsResult>): Int? {
            return state.anchorPosition?.let {
                state.closestPageToPosition(it)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
            }
        }
    }
}