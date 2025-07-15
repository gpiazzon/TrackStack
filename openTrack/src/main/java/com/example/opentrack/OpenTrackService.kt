package com.example.opentrack

import com.example.data.entity.Competition
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTrackService {
    @GET("/api/v1/competitions")
    suspend fun getCompetitions(
        @Query("athlete_id") athleteId: Long,
        @Query("from_date") fromDate: String = "2025-01-01"
    ): List<Competition>
}
