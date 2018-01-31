package com.karntrehan.posts.details

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.karntrehan.posts.R

class DetailsActivity : AppCompatActivity() {

    companion object {
        private const val POST_ID = "post_id"
        fun start(context: Context, postId: Int) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(POST_ID, postId)
            context.startActivity(intent)
        }
    }

    private val TAG = "DetailsActivity"
    private var selectedPostId: Int? = null
    private val context: Context  by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        getIntentData()
    }

    private fun getIntentData() {
        selectedPostId = intent.getIntExtra(POST_ID, -1)

        if (selectedPostId == -1) {
            Log.d(TAG, "getIntentData: could not find post Id to get details")
            finish()
            return
        }

        Toast.makeText(context, "Pos: " + selectedPostId, Toast.LENGTH_LONG).show()
    }
}
