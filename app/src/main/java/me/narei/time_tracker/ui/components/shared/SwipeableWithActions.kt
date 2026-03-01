package me.narei.time_tracker.ui.components.shared

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import me.narei.time_tracker.ui.theme.spacing
import kotlin.math.roundToInt

@Composable
fun SwipeableWithActions(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    actions: @Composable () -> Unit
) {

    var contextMenuWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .onSizeChanged { contextMenuWidth = it.width.toFloat() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = MaterialTheme.spacing.medium)
            ) {
                actions()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(true) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount).coerceIn(-contextMenuWidth, 0f)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value <= -(contextMenuWidth / 2f) -> {
                                    scope.launch {
                                        offset.animateTo(-contextMenuWidth)
                                    }
                                }
                                else -> {
                                    scope.launch {
                                        offset.animateTo(0f)
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            content()
        }
    }

}