package com.example.gnbmarketplace.data.cache

import android.app.Activity
import android.content.Context

class SharedPreferencesManager(val context: Context) {

    private val sharedPreferences by lazy { context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE) }
    private val editor by lazy { sharedPreferences.edit() }

    companion object {
        private const val PREF_NAME = "kotlin_preferences"


    }


}