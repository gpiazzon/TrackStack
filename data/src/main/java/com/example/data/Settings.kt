package com.example.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Settings @Inject constructor(@ApplicationContext context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var athleteId: Long
        get() = prefs.getLong("athlete_id", 0L)
        set(value) { prefs.edit().putLong("athlete_id", value).apply() }
}
