package com.example.trackstack

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.example.data.entity.DayRoutine
import com.example.data.entity.TrainingPeriod
import java.time.LocalDate

// Data model capturing routines assigned to AM/PM for a day
private data class DayAssignments(
    val am: MutableList<Routine> = mutableListOf(),
    val pm: MutableList<Routine> = mutableListOf()
)

/**
 * Screen for building a training programme by placing [Routine]s on a week grid.
 *
 * @param period TrainingPeriod to build for
 * @param routines available routines to drag from the bottom sheet
 * @param onRoutineDragged callback invoked after a routine is dropped on a day
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgrammeBuilderScreen(
    period: TrainingPeriod,
    routines: List<Routine>,
    onRoutineDragged: (DayRoutine) -> Unit
) {
    // state of assignments for each date
    val assignments = remember { mutableStateMapOf<LocalDate, DayAssignments>() }
    // bounding rects for day cells
    val cellBounds = remember { mutableMapOf<LocalDate, Rect>() }
    // snackbar to support undo
    val snackbarHost = remember { SnackbarHostState() }

    var draggingRoutine by remember { mutableStateOf<Routine?>(null) }
    var dragPosition by remember { mutableStateOf(Offset.Zero) }

    // Build list of all days within the period
    val days = remember(period) {
        generateSequence(period.startDate) { next ->
            val d = next.plusDays(1)
            if (d <= period.endDate) d else null
        }.toList() + period.endDate
    }
    val weeks = days.chunked(7)

    fun handleDrop() {
        val routine = draggingRoutine ?: return
        // find containing cell
        val entry = cellBounds.entries.firstOrNull { it.value.contains(dragPosition) }
        val date = entry?.key ?: return
        val bounds = entry.value
        val amPm = if (dragPosition.x - bounds.left < bounds.width / 2f) 0 else 1
        val dayAssignments = assignments.getOrPut(date) { DayAssignments() }
        val list = if (amPm == 0) dayAssignments.am else dayAssignments.pm
        list.add(routine)
        val dayRoutine = DayRoutine(date, routine.id.toLong(), amPm, "")
        onRoutineDragged(dayRoutine)
        draggingRoutine = null
        // snackbar with undo
        LaunchedEffect(dayRoutine) {
            val result = snackbarHost.showSnackbar(
                message = "Added ${routine.title} on $date",
                actionLabel = "Undo"
            )
            if (result == SnackbarResult.ActionPerformed) {
                list.remove(routine)
            }
        }
    }

    BottomSheetScaffold(
        sheetContent = {
            LazyColumn {
                items(routines.size) { idx ->
                    val routine = routines[idx]
                    RoutineCard(
                        routine,
                        modifier = Modifier.pointerInput(routine) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    draggingRoutine = routine
                                    dragPosition = offset
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    dragPosition += dragAmount
                                },
                                onDragEnd = {
                                    handleDrop()
                                },
                                onDragCancel = { draggingRoutine = null }
                            )
                        }
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHost) }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            Button(onClick = {
                if (weeks.isNotEmpty()) {
                    val firstWeek = weeks.first()
                    val pattern = firstWeek.map { assignments[it] }
                    weeks.drop(1).forEach { week ->
                        week.forEachIndexed { index, day ->
                            pattern[index]?.let { src ->
                                val dst = assignments.getOrPut(day) { DayAssignments() }
                                dst.am.clear(); dst.am.addAll(src.am)
                                dst.pm.clear(); dst.pm.addAll(src.pm)
                            }
                        }
                    }
                }
            }) { Text("Auto-repeat") }

            LazyVerticalGrid(columns = GridCells.Fixed(7)) {
                weeks.flatten().forEach { day ->
                    item {
                        val assign = assignments[day]
                        Column(
                            modifier = Modifier
                                .height(80.dp)
                                .fillMaxWidth()
                                .onGloballyPositioned { coor ->
                                    cellBounds[day] = coor.boundsInRoot()
                                }
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Text(day.dayOfWeek.name.take(3))
                            assign?.am?.forEach { Text("AM: ${it.title}") }
                            assign?.pm?.forEach { Text("PM: ${it.title}") }
                        }
                    }
                }
            }
        }
    }
}

