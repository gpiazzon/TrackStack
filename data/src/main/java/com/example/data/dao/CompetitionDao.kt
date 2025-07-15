package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entity.Competition
import java.time.LocalDate

@Dao
interface CompetitionDao {
    @Query("SELECT * FROM Competition WHERE date >= :fromDate ORDER BY date")
    suspend fun getUpcoming(fromDate: LocalDate): List<Competition>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<Competition>)
}
