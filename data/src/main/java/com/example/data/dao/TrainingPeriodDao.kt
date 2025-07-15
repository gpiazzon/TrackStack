package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entity.TrainingPeriod

@Dao
interface TrainingPeriodDao {
    @Query("SELECT * FROM TrainingPeriod")
    suspend fun getAll(): List<TrainingPeriod>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(period: TrainingPeriod)
}
