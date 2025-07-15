package com.example.trackstack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun StatsScreen(viewModel: StatsViewModel = hiltViewModel()) {
    val rankingDeltas by viewModel.rankingDeltas.collectAsState()
    val scrollState = rememberScrollState()

    Surface {
        Column(Modifier.verticalScroll(scrollState).padding(16.dp)) {
            Text("7-day Completion", style = MaterialTheme.typography.titleMedium)
            LineChartView(viewModel.completionPercentages)

            Spacer(Modifier.height(16.dp))

            Text("Weekly Routine Counts", style = MaterialTheme.typography.titleMedium)
            BarChartView(viewModel.categories, viewModel.weeklyCounts)

            Spacer(Modifier.height(16.dp))

            Text("Ranking Changes", style = MaterialTheme.typography.titleMedium)
            rankingDeltas.forEach { delta ->
                Text("${delta.event}: ${delta.newPos} (${delta.oldPos - delta.newPos})")
            }
        }
    }
}

@Composable
private fun LineChartView(values: List<Float>) {
    val entries = remember(values) {
        values.mapIndexed { index, v -> Entry(index.toFloat(), v) }
    }
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                description = Description().apply { text = "" }
                axisRight.isEnabled = false
                data = LineData(LineDataSet(entries, "Completion %").apply {
                    setDrawValues(false)
                })
                animateX(500)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { chart ->
        chart.data.getDataSetByIndex(0).clear()
        entries.forEach { chart.data.addEntry(it, 0) }
        chart.data.notifyDataChanged()
        chart.notifyDataSetChanged()
    }
}

@Composable
private fun BarChartView(categories: List<String>, weekly: List<FloatArray>) {
    val entries = remember(weekly) {
        weekly.mapIndexed { index, counts ->
            BarEntry(index.toFloat(), counts)
        }
    }
    val set = remember(categories) {
        BarDataSet(entries, "Routines").apply {
            setDrawValues(false)
            setColors(*intArrayOf(0xFF90CAF9.toInt(), 0xFFA5D6A7.toInt(), 0xFFFFF59D.toInt()))
            stackLabels = categories.toTypedArray()
        }
    }
    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                description = Description().apply { text = "" }
                axisRight.isEnabled = false
                data = BarData(set)
                animateY(500)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { chart ->
        chart.data = BarData(set)
        chart.invalidate()
    }
}

