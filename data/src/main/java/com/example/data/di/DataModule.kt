package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.Repository
import com.example.data.Settings
import com.example.data.TrackStackDatabase
import com.example.data.RankingDeltaSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TrackStackDatabase {
        return Room.databaseBuilder(
            context,
            TrackStackDatabase::class.java,
            "trackstack.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: TrackStackDatabase): Repository = Repository(db)

    @Provides
    @Singleton
    fun provideSettings(@ApplicationContext context: Context): Settings = Settings(context)

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideRankingSource(settings: Settings, database: FirebaseDatabase): RankingDeltaSource =
        RankingDeltaSource(settings, database)
}
