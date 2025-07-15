package com.example.data.entity

import androidx.room.Entity
import java.time.LocalDate

@Entity(primaryKeys = ["dayId", "routineId", "amPm"])
data class DayRoutine(
    val dayId: LocalDate,
    val routineId: Long,
    val amPm: Int,
    val customPayloadJson: String
)
