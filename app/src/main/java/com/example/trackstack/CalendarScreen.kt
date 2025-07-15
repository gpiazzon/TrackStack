package com.example.trackstack

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/** Simple routine item kept in memory. */
data class RoutineItem(
    val id: Long,
    val title: String,
    val amPm: Int,
    var completed: Boolean = false
)

data class DayInfo(
    val date: LocalDate,
    val routines: MutableList<RoutineItem> = mutableListOf(),
    var hasCompetition: Boolean = false
)

@Composable
fun CalendarScreen() {
    val month = YearMonth.now()
    val firstDay = month.atDay(1)
    val monthDays = remember(month) {
        (1..month.lengthOfMonth()).map { firstDay.plusDays((it - 1).toLong()) }
    }
    val startOffset = firstDay.dayOfWeek.ordinal % 7
    val cells = List(startOffset) { null } + monthDays

    val dayMap = remember { mutableStateMapOf<LocalDate, DayInfo>() }
    var selectedDay by remember { mutableStateOf<DayInfo?>(null) }

    Box {
        Column {
            Text(
                month.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
            LazyVerticalGrid(columns = GridCells.Fixed(7)) {
                items(cells) { date ->
                    if (date == null) {
                        Box(modifier = Modifier.size(48.dp))
                    } else {
                        val info = dayMap.getOrPut(date) { DayInfo(date) }
                        DayCell(info) { selectedDay = info }
                    }
                }
            }
        }
        selectedDay?.let { info ->
            DayDetailBottomSheet(
                info = info,
                onDismiss = { selectedDay = null }
            )
        }
    }
}

@Composable
private fun DayCell(info: DayInfo, onClick: () -> Unit) {
    val completion = if (info.routines.isEmpty()) 0 else 100 * info.routines.count { it.completed } / info.routines.size
    val color = if (completion == 100) Color(0xFF81C784) else Color(0xFFB0B0B0)
    Box(
        modifier = Modifier
            .size(48.dp)
            .padding(2.dp)
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = info.date.dayOfMonth.toString(), fontSize = 12.sp)
        if (info.hasCompetition) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(8.dp)
                    .background(Color(0xFF9C27B0), shape = MaterialTheme.shapes.small)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDetailBottomSheet(info: DayInfo, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val routines = remember(info) { info.routines }
    val dayCompleted by remember {
        derivedStateOf { routines.isNotEmpty() && routines.all { it.completed } }
    }
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Box {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("${info.date}", fontWeight = FontWeight.Bold)
                routines.forEach { routine ->
                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            if (it == DismissValue.DismissedToStart) {
                                routines.remove(routine)
                                true
                            } else false
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        background = {},
                        dismissContent = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            ) {
                                Checkbox(
                                    checked = routine.completed,
                                    onCheckedChange = { checked -> routine.completed = checked }
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(routine.title)
                            }
                        }
                    )
                }
            }
            CelebrationOverlay(show = dayCompleted)
        }
    }
}

