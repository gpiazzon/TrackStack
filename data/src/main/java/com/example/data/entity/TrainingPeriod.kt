package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class TrainingPeriod(
    @PrimaryKey val id: Long,
    val name: String,
    val color: Long,
    val startDate: LocalDate,
    val endDate: LocalDate
)
