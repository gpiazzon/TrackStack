package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entity.Routine

@Dao
interface RoutineDao {
    @Query("SELECT * FROM Routine")
    suspend fun getAll(): List<Routine>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(routine: Routine)
}
