package com.example.trackstack

import android.app.Application
import com.example.opentrack.SyncCompetitionsWorker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TrackStackApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SyncCompetitionsWorker.schedule(this)
    }
}
