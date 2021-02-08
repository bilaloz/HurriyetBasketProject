package com.hurriyet.basketapp.helper


import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class MySharedPreferences {

    companion object {

        private var preferences: SharedPreferences? = null

        private val PREFERENCES_TIME = "preferencestime"

        @Volatile
        private var instance: MySharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context): MySharedPreferences = instance ?: synchronized(
            lock
        ) {
            instance ?: createCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun createCustomSharedPreferences(context: Context): MySharedPreferences {
            preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return MySharedPreferences()
        }
    }

    fun addTime(time: Long) {
        preferences?.edit(commit = true) {
            putLong(PREFERENCES_TIME, time)
        }
    }

    fun getTime() = preferences?.getLong(PREFERENCES_TIME, 0)

}