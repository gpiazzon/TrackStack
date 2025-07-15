package com.example.data

import com.example.data.entity.Competition
import com.example.data.entity.DayRoutine
import com.example.data.entity.Routine
import com.example.data.entity.TrainingPeriod
import java.time.LocalDate
import javax.inject.Inject

class Repository @Inject constructor(db: TrackStackDatabase) {
    private val routineDao = db.routineDao()
    private val periodDao = db.trainingPeriodDao()
    private val dayRoutineDao = db.dayRoutineDao()
    private val competitionDao = db.competitionDao()

    suspend fun getAllRoutines(): List<Routine> = routineDao.getAll()
    suspend fun insertRoutine(routine: Routine) = routineDao.insert(routine)
    suspend fun getTrainingPeriods(): List<TrainingPeriod> = periodDao.getAll()
    suspend fun insertTrainingPeriod(period: TrainingPeriod) = periodDao.insert(period)
    suspend fun upsertDayRoutine(dayRoutine: DayRoutine) = dayRoutineDao.upsert(dayRoutine)

    suspend fun upsertCompetitions(items: List<Competition>) = competitionDao.upsertAll(items)

    suspend fun getUpcomingCompetitions(fromDate: LocalDate = LocalDate.now()): List<Competition> =
        competitionDao.getUpcoming(fromDate)
}
