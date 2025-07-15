// ─── build.gradle.kts (project root) ───────────────────────────────────────────
plugins {
    // Publish plugin versions once; modules just reference them
    id("com.android.application")            version "8.5.0" apply false
    id("com.android.library")                version "8.5.0" apply false
    kotlin("android")                        version "2.0.0" apply false
    kotlin("kapt")                           version "2.0.0" apply false
    id("com.google.dagger.hilt.android")     version "2.51"  apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }

    // Unified Java 17 tool-chain for every module
    extensions.configure<JavaPluginExtension>("java") {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}
// ───────────────────────────────────────────────────────────────────────────────
