package com.karntrehan.posts.details.model

import com.karntrehan.posts.commons.testing.DummyData
import com.karntrehan.posts.core.testing.TestScheduler
import com.karntrehan.posts.commons.data.local.Comment
import com.mpaani.core.networking.Outcome
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

/**
 * Tests for [DetailsRepository]
 * */
@RunWith(RobolectricTestRunner::class)
class DetailsRepositoryTest {
    private lateinit var repository: DetailsRepository
    private val local: DetailsDataContract.Local = mock()
    private val remote: DetailsDataContract.Remote = mock()
    private val compositeDisposable = CompositeDisposable()
    private val postId = 1

    @Before
    fun init() {
        repository = DetailsRepository(local, remote, TestScheduler(), compositeDisposable)
        whenever(local.getCommentsForPost(postId)).doReturn(Flowable.just(emptyList()))
        whenever(remote.getCommentsForPost(postId)).doReturn(Flowable.just(emptyList()))
    }

    /**
     * Verify if calling [DetailsRepository.fetchCommentsFor] triggers [DetailsDataContract.Local.getCommentsForPost]
     *  and it's result is added to the [DetailsRepository.commentsFetchOutcome]
     * */
    @Test
    fun testFetchCommentsFor() {
        val comments = listOf(DummyData.Comment(postId, 1), DummyData.Comment(postId, 2))
        whenever(local.getCommentsForPost(postId)).doReturn(Flowable.just(comments))

        val testObs = TestObserver<Outcome<List<Comment>>>()
        repository.commentsFetchOutcome.subscribe(testObs)

        testObs.assertEmpty()

        repository.fetchCommentsFor(postId)

        verify(local).getCommentsForPost(postId)

        testObs.assertValueAt(0, Outcome.loading(true))
        testObs.assertValueAt(1, Outcome.loading(false))
        testObs.assertValueAt(2, Outcome.success(comments))
    }

    /**
     * Verify if calling [DetailsRepository.fetchCommentsFor] triggers [DetailsDataContract.Remote.getCommentsForPost]
     * if [DetailsRepository.remoteFetch] = true
     * */
    @Test
    fun testFirstFetchPostsTriggersRemote() {
        repository.remoteFetch = true
        repository.fetchCommentsFor(postId)
        verify(remote).getCommentsForPost(postId)
    }


    /**
     * Verify if calling [DetailsRepository.fetchCommentsFor] NEVER triggers [DetailsDataContract.Remote.getCommentsForPost]
     *  if [DetailsRepository.remoteFetch] = false
     * */
    @Test
    fun testSubsequentFetchPostsNeverTriggersRemote() {
        repository.remoteFetch = false
        repository.fetchCommentsFor(postId)
        verify(remote, never()).getCommentsForPost(postId)
    }


    /**
     * Verify successful refresh of posts and users triggers [DetailsDataContract.Local.saveComments]
     * */
    @Test
    fun testRefreshPostsTriggersSave() {
        val dummyComments = listOf(DummyData.Comment(postId, 1), DummyData.Comment(postId, 2))
        whenever(remote.getCommentsForPost(postId)).doReturn(Flowable.just(dummyComments))

        repository.refreshComments(postId)
        verify(local).saveComments(dummyComments)
    }

    /**
     * Verify erred refresh of posts and users pushes to [DetailsDataContract.Repository.commentsFetchOutcome]
     * with error
     * */
    @Test
    fun testRefreshPostsFailurePushesToOutcome() {
        val exception = IOException()
        whenever(remote.getCommentsForPost(postId)).doReturn(Flowable.error(exception))

        val obs = TestObserver<Outcome<List<Comment>>>()
        repository.commentsFetchOutcome.subscribe(obs)

        repository.refreshComments(postId)

        obs.assertValueAt(0, Outcome.loading(true))
        obs.assertValueAt(1, Outcome.loading(false))
        obs.assertValueAt(2, Outcome.failure(exception))
    }
}