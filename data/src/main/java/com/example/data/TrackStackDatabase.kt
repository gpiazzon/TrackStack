package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.dao.CompetitionDao
import com.example.data.dao.DayRoutineDao
import com.example.data.dao.RoutineDao
import com.example.data.dao.TrainingPeriodDao
import com.example.data.entity.Converters
import com.example.data.entity.Competition
import com.example.data.entity.DayRoutine
import com.example.data.entity.Routine
import com.example.data.entity.TrainingPeriod

@Database(
    entities = [Routine::class, TrainingPeriod::class, DayRoutine::class, Competition::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TrackStackDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
    abstract fun trainingPeriodDao(): TrainingPeriodDao
    abstract fun dayRoutineDao(): DayRoutineDao
    abstract fun competitionDao(): CompetitionDao
}
