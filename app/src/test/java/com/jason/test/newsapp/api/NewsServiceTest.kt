package com.jason.test.newsapp.api

import com.google.gson.JsonSyntaxException
import com.jason.test.newsapp.TestHelper
import com.jason.test.newsapp.network.NewsService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.EOFException

@ExperimentalCoroutinesApi
class NewsServiceTest {
    private lateinit var service: NewsService
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }

    @After
    fun dropdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `retrieve correct DataSet`() {
        enqueueResponse("news.json")
        val mockResponse = runBlocking {
            service.search(1, "football")
        }
        val request = mockWebServer.takeRequest()
        assertThat(request.path, CoreMatchers.`is`("/search?page=1&q=football"))
        assertThat(mockResponse, CoreMatchers.`is`(IsNull.notNullValue()))
        assertThat(mockResponse.response, CoreMatchers.`is`(IsNull.notNullValue()))

        mockResponse.response.apply {
            Assert.assertEquals(this.status, "ok")
            Assert.assertEquals(this.userTier, "developer")
            Assert.assertEquals(this.total, 210549)
            Assert.assertEquals(this.startIndex, 1)
            Assert.assertEquals(this.pageSize, 10)
            Assert.assertEquals(this.currentPage, 1)
            Assert.assertEquals(this.pages, 21055)
            Assert.assertEquals(this.orderBy, "relevance")
        }

        mockResponse.response.results?.forEach { newsDetail ->
            assertThat(newsDetail.id, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(newsDetail.type, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(newsDetail.sectionId, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(newsDetail.sectionName, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(newsDetail.webPublicationDate, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(newsDetail.webTitle, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(newsDetail.webUrl, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(newsDetail.apiUrl, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(newsDetail.isHosted, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(newsDetail.isHosted, CoreMatchers.`is`(instanceOf(Boolean::class.java)))
            assertThat(newsDetail.pillarId, CoreMatchers.`is`(IsNull.notNullValue()))
            assertThat(newsDetail.pillarId, CoreMatchers.`is`(IsNull.notNullValue()))
        }
    }


    @Test(expected = EOFException::class)
    fun `retrieve EOFException when api return null`() {
        enqueueResponse("null.json")

        runBlocking {
            service.search(1, "football")
        }

        val request = mockWebServer.takeRequest()
        assertThat(request.path, CoreMatchers.`is`("/search?page=1&q=football"))
    }

    @Test(expected = JsonSyntaxException::class)
    fun `retrieve JsonSyntaxException when api return array`() {
        enqueueResponse("array.json")

        runBlocking {
            service.search(1, "football")
        }

        val request = mockWebServer.takeRequest()
        assertThat(request.path, CoreMatchers.`is`("/search?page=1&q=football"))
    }

    private fun enqueueResponse(fileName: String) {
        val source = TestHelper.getJsonString(fileName)
        val mockResponse = MockResponse()

        mockWebServer.enqueue(
            mockResponse
                .setBody(source)
        )
    }
}