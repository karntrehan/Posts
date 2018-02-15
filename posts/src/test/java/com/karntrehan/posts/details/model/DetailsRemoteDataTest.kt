package  com.karntrehan.posts.details.model

import com.karntrehan.posts.commons.testing.DummyData
import com.karntrehan.posts.commons.data.remote.PostService
import com.karntrehan.posts.list.model.ListRemoteData
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for [ListRemoteData]
 */

@RunWith(RobolectricTestRunner::class)
class DetailsRemoteDataTest {
    private val postService = mock<PostService>()

    @Test
    fun getCommentsForPost() {
        val userId = 1
        whenever(postService.getComments(userId)).thenReturn(Flowable.just(listOf(
                DummyData.Comment(userId, 101),
                DummyData.Comment(userId, 102),
                DummyData.Comment(userId, 103)
        )))

        DetailsRemoteData(postService).getCommentsForPost(userId).test().run {
            assertNoErrors()
            assertValueCount(1)
            assertEquals(values()[0].size, 3)
            assertEquals(values()[0][0].id, 101)
            assertEquals(values()[0][1].id, 102)
            assertEquals(values()[0][2].id, 103)
        }
    }
}