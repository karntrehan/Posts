package com.karntrehan.posts.commons.data.remote

import com.karntrehan.posts.core.testing.DependencyProvider
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

/**
 * Tests for [PostService]
 */
@RunWith(RobolectricTestRunner::class)
class PostServiceTest {

    private lateinit var postService: PostService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        mockWebServer = MockWebServer()
        postService = DependencyProvider
                .getRetrofit(mockWebServer.url("/"))
                .create(PostService::class.java)

    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getUsers() {
        queueResponse {
            setResponseCode(200)
            setBody(DependencyProvider.getResponseFromJson("users"))
        }

        postService
                .getUsers()
                .test()
                .run {
                    assertNoErrors()
                    assertValueCount(1)
                    Assert.assertEquals(values()[0].size, 10)
                    Assert.assertEquals(values()[0][0].userName, "Leanne Graham")
                    Assert.assertEquals(values()[0][0].id, 1)
                }
    }

    @Test
    fun getPosts() {
        queueResponse {
            setResponseCode(200)
            setBody(DependencyProvider.getResponseFromJson("posts"))
        }

        postService
                .getPosts()
                .test()
                .run {
                    assertNoErrors()
                    assertValueCount(1)
                    Assert.assertEquals(values()[0].size, 10)
                    Assert.assertEquals(values()[0][0].postTitle, "sunt aut facere repellat " +
                            "provident occaecati excepturi optio reprehenderit")
                    Assert.assertEquals(values()[0][0].userId, 1)
                }
    }

    @Test
    fun getComments() {
        queueResponse {
            setResponseCode(200)
            setBody(DependencyProvider.getResponseFromJson("comments"))
        }

        postService
                .getComments(1)
                .test()
                .run {
                    assertNoErrors()
                    assertValueCount(1)
                    Assert.assertEquals(values()[0].size, 5)
                    Assert.assertEquals(values()[0][0].id, 1)
                    Assert.assertEquals(values()[0][0].email, "Eliseo@gardner.biz")
                }
    }


    private fun queueResponse(block: MockResponse.() -> Unit) {
        mockWebServer.enqueue(MockResponse().apply(block))
    }
}