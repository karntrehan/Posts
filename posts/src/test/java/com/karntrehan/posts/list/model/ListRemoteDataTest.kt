package  com.karntrehan.posts.list.model

import com.karntrehan.posts.core.testing.DependencyProvider
import com.karntrehan.posts.commons.data.remote.PostService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

/**
 * Tests for [ListRemoteData]
 */

@RunWith(RobolectricTestRunner::class)
class ListRemoteDataTest {

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
        mockWebServer.apply {
            enqueue(MockResponse().apply {
                setResponseCode(200)
                setBody(DependencyProvider.getResponseFromJson("users"))
            })
        }

        ListRemoteData(postService)
                .getUsers()
                .test()
                .run {
                    assertNoErrors()
                    assertValueCount(1)
                    assertEquals(values()[0].size, 10)
                    assertEquals(values()[0][0].userName, "Leanne Graham")
                    assertEquals(values()[0][0].id, 1)
                }
    }

    @Test
    fun getPosts() {
        mockWebServer.apply {
            enqueue(MockResponse().apply {
                setResponseCode(200)
                setBody(DependencyProvider.getResponseFromJson("posts"))
            })
        }

        ListRemoteData(postService)
                .getPosts()
                .test()
                .run {
                    assertNoErrors()
                    assertValueCount(1)
                    assertEquals(values()[0].size, 10)
                    assertEquals(values()[0][0].postTitle, "sunt aut facere repellat " +
                            "provident occaecati excepturi optio reprehenderit")
                    assertEquals(values()[0][0].userId, 1)
                }
    }
}