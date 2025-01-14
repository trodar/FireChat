package com.trodar.ui.audio

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trodar.designtemplate.theme.FireChatTheme
import com.trodar.ui.extension.lerp
import com.trodar.ui.extension.toPxf
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.random.Random

class ProgressBarState(duration: String) {
    var isPlaying by mutableStateOf(false)
    var elapsedTime by mutableStateOf("00:00")
    var timeLeft by mutableStateOf(duration)
}

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    songDuration: String,
) {
    val dateFormatter = remember { PlayTimeFormatter() }
    val progressBarState = remember { ProgressBarState(songDuration) }
    var currentTime by remember { mutableLongStateOf(0L) }

    if (progressBarState.isPlaying) {
        LaunchedEffect(key1 = progressBarState.isPlaying) {
            val songTime = 200L
            while (isActive) {
                progressBarState.elapsedTime = dateFormatter.format(currentTime)
                progressBarState.timeLeft = dateFormatter.format(songTime - currentTime)

                delay(1000L)

                currentTime += 1
                if (currentTime > songTime) {
                    currentTime = 0
                }
            }
        }
    }
    progressBarState.isPlaying = true
    ProgressBar(
        modifier = modifier,
        state = progressBarState,
    )
}

@Composable
fun ProgressBar(
    modifier: Modifier,
    state: ProgressBarState,
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVolumeLevelBar(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(48.dp),
                barWidth = 3.dp,
                gapWidth = 2.dp,
                isAnimating = state.isPlaying,
            )
        }

    }
}

@Composable
fun AnimatedVolumeLevelBar(
    modifier: Modifier = Modifier,
    barWidth: Dp = 2.dp,
    gapWidth: Dp = 2.dp,
    barColor: Color = MaterialTheme.colorScheme.onSurface,
    isAnimating: Boolean = false,
) {
    val infiniteAnimation = rememberInfiniteTransition(label = "")
    val animations = mutableListOf<State<Float>>()
    val random = remember { Random(System.currentTimeMillis()) }

    repeat(15) {
        val durationMillis = random.nextInt(500, 2000)
        animations += infiniteAnimation.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis),
                repeatMode = RepeatMode.Reverse,
            ),
            label = ""
        )
    }

    val barWidthFloat by rememberUpdatedState(newValue = barWidth.toPxf())
    val gapWidthFloat by rememberUpdatedState(newValue = gapWidth.toPxf())

    val initialMultipliers = remember {
        mutableListOf<Float>().apply {
            repeat(MaxLinesCount) { this += random.nextFloat() }
        }
    }

    val heightDivider by animateFloatAsState(
        targetValue = if (isAnimating) 1f else 6f,
        animationSpec = tween(1000, easing = LinearEasing),
        label = ""
    )

    Canvas(modifier = modifier) {
        val canvasHeight = size.height
        val canvasWidth = size.width
        val canvasCenterY = canvasHeight / 2f

        val count =
            (canvasWidth / (barWidthFloat + gapWidthFloat)).toInt().coerceAtMost(MaxLinesCount)
        val animatedVolumeWidth = count * (barWidthFloat + gapWidthFloat)
        var startOffset = (canvasWidth - animatedVolumeWidth) / 2

        val barMinHeight = 0f
        val barMaxHeight = canvasHeight / 2f / heightDivider

        repeat(count) { index ->
            val currentSize = animations[index % animations.size].value
            var barHeightPercent = initialMultipliers[index] + currentSize
            if (barHeightPercent > 1.0f) {
                val diff = barHeightPercent - 1.0f
                barHeightPercent = 1.0f - diff
            }
            val barHeight = lerp(barMinHeight, barMaxHeight, barHeightPercent)

            drawLine(
                color = barColor,
                start = Offset(startOffset, canvasCenterY - barHeight / 2),
                end = Offset(startOffset, canvasCenterY + barHeight / 2),
                strokeWidth = barWidthFloat,
                cap = StrokeCap.Round,
            )
            startOffset += barWidthFloat + gapWidthFloat
        }
    }
}

const val MaxLinesCount = 40

@Preview
@Composable
fun ProgressBarPreview() {
    FireChatTheme {
        ProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            state = ProgressBarState("03:20"),
        )
    }
}