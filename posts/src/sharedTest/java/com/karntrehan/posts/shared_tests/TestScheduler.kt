package com.karntrehan.posts.commons

import com.karntrehan.posts.core.networking.Scheduler
import io.reactivex.schedulers.Schedulers

class TestScheduler : Scheduler {

    override fun mainThread(): io.reactivex.Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): io.reactivex.Scheduler {
        return Schedulers.trampoline()
    }
}