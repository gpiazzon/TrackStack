package com.example.trackstack

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.draggedItem
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState
import org.burnoutcrew.reorderable.reorderable

// Simple data model representing a routine.
data class Routine(val id: Int, val title: String, val emoji: String)

// A card displaying an emoji, title and a drag handle icon.
@Composable
fun RoutineCard(routine: Routine, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            Text(routine.emoji)
            Spacer(Modifier.width(8.dp))
            Text(routine.title, modifier = Modifier.weight(1f))
            Icon(Icons.Default.DragHandle, contentDescription = "Drag")
        }
    }
}

/**
 * Screen showing a reorderable grid of [Routine] items.
 *
 * @param onRoutineDragged callback invoked after a routine has been moved.
 */
@Composable
fun TemplateLibraryScreen(onRoutineDragged: (Routine) -> Unit = {}) {
    var routines = remember {
        mutableStateListOf(
            Routine(1, "Morning", "\uD83C\uDF05"),
            Routine(2, "Workout", "\uD83C\uDFCB\uFE0F"),
            Routine(3, "Study", "\uD83D\uDCDA"),
            Routine(4, "Evening", "\uD83C\uDF03")
        )
    }

    val state = rememberReorderableLazyGridState(onMove = { from, to ->
        routines.add(to.index, routines.removeAt(from.index))
        onRoutineDragged(routines[to.index])
    })

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = state.gridState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(routines, key = { it.id }) { routine ->
            RoutineCard(
                routine,
                modifier = Modifier.draggedItem(state.offsetByKey(routine.id))
            )
        }
    }
}
