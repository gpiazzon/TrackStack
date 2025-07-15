import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RankingDelta
import com.example.data.RankingDeltaSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    rankingSource: RankingDeltaSource
) : ViewModel() {

    val rankingDeltas = rankingSource.rankingDeltas()
        .scan(emptyList<RankingDelta>()) { acc, delta ->
            (listOf(delta) + acc).take(5)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Sample completion percentages for the last 14 days
    val completionPercentages = List(14) { (60..100).random().toFloat() }

    // Sample routine category counts per week
    val categories = listOf("Run", "Strength", "Rest")
    val weeklyCounts = listOf(
        floatArrayOf(3f, 2f, 2f),
        floatArrayOf(4f, 1f, 2f),
        floatArrayOf(2f, 3f, 2f)
    )
}
