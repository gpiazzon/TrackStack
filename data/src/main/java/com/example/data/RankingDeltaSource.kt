package com.example.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RankingDeltaSource @Inject constructor(
    private val settings: Settings,
    private val database: FirebaseDatabase
) {
    fun rankingDeltas(): Flow<RankingDelta> = callbackFlow {
        val ref = database.getReference("rankings/${'$'}{settings.athleteId}")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val event = snapshot.child("event").getValue(String::class.java) ?: return
                val oldPos = snapshot.child("oldPos").getValue(Int::class.java) ?: return
                val newPos = snapshot.child("newPos").getValue(Int::class.java) ?: return
                trySend(RankingDelta(event, oldPos, newPos))
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }
}
