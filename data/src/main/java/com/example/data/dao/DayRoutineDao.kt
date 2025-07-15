package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.data.entity.DayRoutine

@Dao
interface DayRoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(dayRoutine: DayRoutine)
}
