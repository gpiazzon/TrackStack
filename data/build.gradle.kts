plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.gpiazzon.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:2.1.10"))
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.room:room-runtime:2.7.0")
    implementation("androidx.room:room-ktx:2.7.0")
    kapt("androidx.room:room-compiler:2.7.0")
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-compiler:2.51")
    implementation("com.google.firebase:firebase-database-ktx:21.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
}
