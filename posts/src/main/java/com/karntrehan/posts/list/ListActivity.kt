package com.karntrehan.posts.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.karntrehan.posts.R
import com.karntrehan.posts.commons.PostDH
import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.core.application.BaseActivity
import com.karntrehan.posts.details.DetailsActivity
import com.karntrehan.posts.list.viewmodel.ListViewModel
import com.karntrehan.posts.list.viewmodel.ListViewModelFactory
import com.mpaani.core.networking.Outcome
import kotlinx.android.synthetic.main.activity_list.*
import java.io.IOException
import javax.inject.Inject

class ListActivity : BaseActivity(), ListAdapter.PostInteractor {
    private val component by lazy { PostDH.listComponent() }

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var adapter: ListAdapter

    private val viewModel: ListViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java) }

    private val context: Context by lazy { this }

    private val TAG = "ListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        component.inject(this)

        adapter.interactor = this
        rvPosts.adapter = adapter
        srlPosts.setOnRefreshListener { viewModel.refreshPosts() }

        viewModel.getPosts()
        initiateDataListener()
    }

    private fun initiateDataListener() {
        //Observe the outcome and update state of the screen  accordingly
        viewModel.postsOutcome.observe(this, Observer<Outcome<List<PostWithUser>>> { outcome ->
            Log.d(TAG, "initiateDataListener: " + outcome.toString())
            when (outcome) {

                is Outcome.Progress -> srlPosts.isRefreshing = outcome.loading

                is Outcome.Success -> {
                    Log.d(TAG, "initiateDataListener: Successfully loaded data")
                    adapter.setData(outcome.data)
                }

                is Outcome.Failure -> {
                    if (outcome.e is IOException)
                        Toast.makeText(context, R.string.need_internet_posts, Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(context, R.string.failed_post_try_again, Toast.LENGTH_LONG).show()
                }

            }
        })
    }

    override fun postClicked(post: PostWithUser, tvTitle: TextView, tvBody: TextView, tvAuthorName: TextView, ivAvatar: ImageView) {
        DetailsActivity.start(context, post,tvTitle,tvBody,tvAuthorName,ivAvatar)
    }

}
