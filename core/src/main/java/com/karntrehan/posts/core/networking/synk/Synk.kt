package com.karntrehan.posts.core.networking.synk

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import java.util.concurrent.TimeUnit

/**
 * A Singleton object to identify if a sync operation should run or not.
 * */
object Synk {

    private var preferences: SharedPreferences? = null
    const val TAG = "Synk"
    private const val SYNK_IT = true
    private const val DONT_SYNK = false

    /**
     * Initialize the preference object to read and write sync operation date-times.
     * Preferably should be initialized in the application class.
     **/
    fun init(context: Context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    /**
     * Check if the sync operation should run or not.
     *
     * Note: Requires the [init] function to be called beforehand once per application run time.
     *
     * @param key key of the sync operation
     * @param window amount of the delay between successive syncs
     * @param unit [TimeUnit] of [window]
     * @return [Boolean] to be checked if the sync should run
     *
     * @throws IllegalStateException if [unit] is [TimeUnit.MILLISECONDS], [TimeUnit.NANOSECONDS], [TimeUnit.MICROSECONDS] or [TimeUnit.SECONDS]
     **/
    fun shouldSync(key: String, window: Int = 4, unit: TimeUnit = TimeUnit.HOURS): Boolean {

        performPrefsSanityCheck()

        if (unit == TimeUnit.MILLISECONDS
            || unit == TimeUnit.NANOSECONDS
            || unit == TimeUnit.MICROSECONDS
            || unit == TimeUnit.SECONDS
        )
            throw IllegalStateException("Illegal time window")

        val currentSavedValue = preferences?.getString(key, "")

        if (currentSavedValue.isNullOrEmpty()) //Operation has never run or Synk doesn't know about it
            return syncIt(key)

        val syncedTime = DateTime.parse(currentSavedValue)
        val syncBlock = when (unit) { //Identify the block window from last sync
            TimeUnit.MINUTES -> syncedTime.plusMinutes(window)
            TimeUnit.HOURS -> syncedTime.plusHours(window)
            TimeUnit.DAYS -> syncedTime.plusDays(window)
            else -> syncedTime
        }

        //Is the current time past the sync block window?
        return if (DateTime.now() >= syncBlock) syncIt(key) else DONT_SYNK
    }

    /**
     * Tell Synk that the sync operation was a success.
     *
     * This function saves the current time with the passed key into preferences.
     *
     * @param key String key of the sync operation
     **/
    fun syncSuccess(key: String) {
        performPrefsSanityCheck()
        saveSyncTime(key)
    }

    /**
     * Tell Synk that the sync operation was a failure.
     *
     * This function removes the passed key from preferences.
     *
     * @param key String key of the sync operation
     **/
    fun syncFailure(key: String) {
        performPrefsSanityCheck()
        preferences
            ?.edit()
            ?.remove(key)
            ?.apply()
    }

    /**
     * Checks if the preferences object has been initialized.
     *
     * @throws IllegalStateException if prefs not initialized
     **/
    private fun performPrefsSanityCheck() {
        if (preferences == null)
            throw IllegalStateException("Make sure to init Synk")
    }

    /**
     * Triggers a save into preferences with current time for mentioned key,
     * preventing multiple sync calls if first call is still under progress.
     *
     * @param key key of the sync operation
     **/
    private fun syncIt(key: String): Boolean {
        saveSyncTime(key)
        return SYNK_IT
    }

    /**
     * Saves the mentioned datetime into the preferences in ISODateTimeFormat string.
     *
     * @param key key of the sync operation
     * @param dateTime [DateTime] to be saved.
     * */
    private fun saveSyncTime(key: String, dateTime: DateTime = DateTime.now()) {
        preferences
            ?.edit()
            ?.putString(key, ISODateTimeFormat.dateTime().print(dateTime))
            ?.apply()
    }
}