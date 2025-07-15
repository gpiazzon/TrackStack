pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.4.1"
        id("com.android.library") version "8.4.1"
        id("org.jetbrains.kotlin.android") version "2.0.0"
        id("org.jetbrains.kotlin.kapt") version "2.0.0"
        id("dagger.hilt.android.plugin") version "2.51"
    }
}

rootProject.name = "TrackStack"
include(":app")
include(":data")
include(":po10sync")
include(":openTrack")
