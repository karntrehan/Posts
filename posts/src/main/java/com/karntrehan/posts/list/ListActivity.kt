package com.karntrehan.posts.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.karntrehan.posts.R
import com.karntrehan.posts.details.DetailsActivity
import com.karntrehan.posts.list.data.ListViewModelFactory
import com.karntrehan.posts.list.data.PostWithUser
import com.karntrehan.posts.list.di.ListDH
import com.mpaani.core.networking.Outcome
import kotlinx.android.synthetic.main.activity_list.*
import java.io.IOException
import javax.inject.Inject

class ListActivity : AppCompatActivity(), ListAdapter.PostInteractor {
    private val component by lazy { ListDH.component() }

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var adapter: ListAdapter

    private val storesViewModel: ListViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java) }

    private val context: Context by lazy { this }

    private val TAG = "ListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        component.inject(this)

        setUpToolbar()

        adapter.interactor = this
        rvPosts.adapter = adapter
        srlPosts.setOnRefreshListener { storesViewModel.refreshPosts() }

        storesViewModel.getPosts()
        initiateDataListener()
    }

    private fun initiateDataListener() {
        //Observe the outcome and update state of the screen  accordingly
        storesViewModel.postsOutcome.observe(this, Observer<Outcome<List<PostWithUser>>> { outcome ->
            Log.d(TAG, "initiateDataListener: " + outcome.toString())
            when (outcome) {

                is Outcome.Progress -> srlPosts.isRefreshing = outcome.loading

                is Outcome.Success -> {
                    Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
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

    private fun setUpToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun postClicked(postId: Int) {
        DetailsActivity.start(context, postId)
    }

}
