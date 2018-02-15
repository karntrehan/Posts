package com.karntrehan.posts.details.viewmodel

import android.arch.lifecycle.Observer
import com.karntrehan.posts.commons.testing.DummyData
import com.karntrehan.posts.commons.data.local.Comment
import com.karntrehan.posts.details.model.DetailsDataContract
import com.mpaani.core.networking.Outcome
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException


/**
 * Tests for [DetailsViewModel]
 * */
@RunWith(RobolectricTestRunner::class)
class DetailsViewModelTest {
    private lateinit var viewModel: DetailsViewModel
    private val repo: DetailsDataContract.Repository = mock()
    private val compositeDisposable = CompositeDisposable()
    private val outcome: Observer<Outcome<List<Comment>>> = mock()

    private val postId = 1

    @Before
    fun init() {
        viewModel = DetailsViewModel(repo, compositeDisposable)
        whenever(repo.commentsFetchOutcome).doReturn(PublishSubject.create())
        viewModel.commentsOutcome.observeForever(outcome)
    }


    /**
     * Test [DetailsViewModel.loadCommentsFor] triggers [DetailsDataContract.Repository.fetchCommentsFor] &
     * livedata [DetailsViewModel.commentsOutcome] gets outcomes pushed
     * from [DetailsDataContract.Repository.commentsFetchOutcome]
     * */
    @Test
    fun testLoadCommentsSuccess() {
        viewModel.loadCommentsFor(postId)
        verify(repo).fetchCommentsFor(postId)

        repo.commentsFetchOutcome.onNext(Outcome.loading(true))
        verify(outcome).onChanged(Outcome.loading(true))

        repo.commentsFetchOutcome.onNext(Outcome.loading(false))
        verify(outcome).onChanged(Outcome.loading(false))

        val data = listOf(DummyData.Comment(postId, 1), DummyData.Comment(postId, 2))
        repo.commentsFetchOutcome.onNext(Outcome.success(data))
        verify(outcome).onChanged(Outcome.success(data))
    }

    /**
     * Test that [DetailsDataContract.Repository.commentsFetchOutcome] on exception passes exception to
     * live [DetailsViewModel.commentsOutcome]
     * */
    @Test
    fun testLoadCommentsError() {
        val exception = IOException()
        repo.commentsFetchOutcome.onNext(Outcome.failure(exception))
        verify(outcome).onChanged(Outcome.failure(exception))
    }

    /**
     * Verify [DetailsViewModel.refreshCommentsFor] triggers [DetailsDataContract.Repository.refreshComments]
     **/
    @Test
    fun testRefreshComments() {
        viewModel.refreshCommentsFor(postId)
        verify(repo).refreshComments(postId)
    }
}