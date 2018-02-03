package com.karntrehan.posts.core.application

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

abstract class BaseActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpToolbar()
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
}