package com.karntrehan.posts.details.model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.karntrehan.posts.commons.testing.DummyData
import com.karntrehan.posts.core.testing.TestScheduler
import com.karntrehan.posts.commons.data.local.PostDb
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for [DetailsLocalData]
 **/
@RunWith(AndroidJUnit4::class)
class DetailsLocalDataTest {
    private lateinit var postDb: PostDb

    private val detailsLocalData: DetailsLocalData by lazy {
        DetailsLocalData(
            postDb,
            TestScheduler()
        )
    }

    //Necessary for Room insertions to work
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val postId = 1
    private val userId = 1
    private val dummyComments = listOf(DummyData.Comment(postId, 1), DummyData.Comment(postId, 2))

    @Before
    fun init() {
        postDb = Room
            .inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), PostDb::class.java)
            .allowMainThreadQueries()
            .build()

        /*Need to insert users and posts, in that order, before testing
        or else ForeignKey constraint fails on inserts*/
        val dummyUsers = listOf(DummyData.User(userId))
        val dummyPosts = listOf(DummyData.Post(userId, postId))
        postDb.userDao().upsertAll(dummyUsers)
        postDb.postDao().upsertAll(dummyPosts)
    }

    /**
     * Test that [DetailsLocalData.getCommentsForPost] returns correct values
     * */
    @Test
    fun testGetCommentsForPost() {
        postDb.commentDao().upsertAll(dummyComments)
        detailsLocalData.getCommentsForPost(postId).test().assertValue(dummyComments)
    }

    /**
     * Test that [DetailsLocalData.saveComments] actually inserts the comments into the database
     * */
    @Test
    fun testSaveComments() {
        detailsLocalData.saveComments(dummyComments)
        val comments = postDb.commentDao().getForPost(postId)
        comments.test().assertNoErrors().assertValue(dummyComments)
    }

}