package com.karntrehan.posts.list.viewmodel

import android.arch.lifecycle.Observer
import com.karntrehan.posts.commons.testing.DummyData
import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.list.model.ListDataContract
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
 * Tests for [ListViewModel]
 * */
@RunWith(RobolectricTestRunner::class)
class ListViewModelTest {

    private lateinit var viewModel: ListViewModel

    private val repo: ListDataContract.Repository = mock()

    private val outcome: Observer<Outcome<List<PostWithUser>>> = mock()

    @Before
    fun init() {
        viewModel = ListViewModel(repo, CompositeDisposable())
        whenever(repo.postFetchOutcome).doReturn(PublishSubject.create())
        viewModel.postsOutcome.observeForever(outcome)
    }

    /**
     * Test [ListViewModel.getPosts] triggers [ListDataContract.Repository.fetchPosts] &
     * livedata [ListViewModel.postsOutcome] gets outcomes pushed
     * from [ListDataContract.Repository.postFetchOutcome]
     * */
    @Test
    fun testGetPostsSuccess() {
        viewModel.getPosts()
        verify(repo).fetchPosts()

        repo.postFetchOutcome.onNext(Outcome.loading(true))
        verify(outcome).onChanged(Outcome.loading(true))

        repo.postFetchOutcome.onNext(Outcome.loading(false))
        verify(outcome).onChanged(Outcome.loading(false))

        val data = listOf(DummyData.PostWithUser(1), DummyData.PostWithUser(2))
        repo.postFetchOutcome.onNext(Outcome.success(data))
        verify(outcome).onChanged(Outcome.success(data))
    }

    /**
     * Test that [ListDataContract.Repository.postFetchOutcome] on exception passes exception to
     * live [ListViewModel.postsOutcome]
     * */
    @Test
    fun testGetPostsError() {
        val exception = IOException()
        repo.postFetchOutcome.onNext(Outcome.failure(exception))
        verify(outcome).onChanged(Outcome.failure(exception))
    }

    /**
     * Verify [ListViewModel.refreshPosts] triggers [ListDataContract.Repository.refreshPosts]
     * */
    @Test
    fun testRefreshPosts() {
        viewModel.refreshPosts()
        verify(repo).refreshPosts()
    }
}