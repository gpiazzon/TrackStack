package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Competition(
    @PrimaryKey val id: Long,
    val name: String,
    val date: LocalDate,
    val city: String
)
