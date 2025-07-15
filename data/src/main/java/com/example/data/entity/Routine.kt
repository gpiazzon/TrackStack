package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Routine(
    @PrimaryKey val id: Long,
    val title: String,
    val category: String,
    val emoji: String,
    val jsonPayload: String
)
