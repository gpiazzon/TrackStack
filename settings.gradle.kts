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
        id("com.google.dagger.hilt.android") version "2.51"
        id("com.google.gms.google-services") version "4.4.1"
    }
}

rootProject.name = "TrackStack"
include(":app")
include(":data")
include(":po10sync")
include(":openTrack")
