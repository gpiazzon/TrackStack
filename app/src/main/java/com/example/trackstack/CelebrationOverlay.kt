package com.example.trackstack

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.models.Party
import nl.dionsegijn.konfetti.compose.models.Position
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Composable
fun CelebrationOverlay(show: Boolean) {
    val visible = remember { mutableStateOf(false) }

    LaunchedEffect(show) {
        if (show) {
            visible.value = true
            delay(2000)
            visible.value = false
        }
    }

    AnimatedVisibility(
        visible = visible.value,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = listOf(
                Party(
                    angle = Angle.BOTTOM,
                    spread = Spread.WIDE,
                    emitter = Emitter(duration = 2, TimeUnit.SECONDS).perSecond(100),
                    position = Position.Relative(0.0, 0.0).between(Position.Relative(1.0, 0.0)),
                )
            )
        )
    }
}

