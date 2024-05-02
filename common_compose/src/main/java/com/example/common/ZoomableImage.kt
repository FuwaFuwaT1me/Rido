package com.example.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun ZoomableImage(
    painter: AsyncImagePainter,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    imageAlign: Alignment = Alignment.Center,
    shape: Shape = RectangleShape,
    maxScale: Float = 1f,
    minScale: Float = 3f,
    contentScale: ContentScale = ContentScale.Fit,
    isZoomable: Boolean = true,
    scrollState: ScrollableState? = null
) {
    val coroutineScope = rememberCoroutineScope()

    var scale by remember { mutableStateOf(1f) }
    val offsetX = remember { mutableFloatStateOf(1f) }
    val offsetY = remember { mutableFloatStateOf(1f) }

    val displayMetrics = LocalContext.current.resources.displayMetrics
    val widthPix = displayMetrics.widthPixels
    val heightPix = displayMetrics.heightPixels

    Box(
        modifier = Modifier
            .clip(shape)
            .background(backgroundColor)
            .pointerInput(Unit) {
                if (isZoomable) {
                    awaitEachGesture {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            val calculatedZoom = event.calculateZoom()
                            if (scale * calculatedZoom in 1f..3f) {
                                scale *= event.calculateZoom()
                            }
                            if (scale in 1f..3f) {
                                scrollState?.run {
                                    coroutineScope.launch {
                                        setScrolling(false)
                                    }
                                }
                                val offset = event.calculatePan()
                                val calculatedX = abs(offsetX.floatValue + offset.x)
                                val calculatedY = abs(offsetY.floatValue + offset.y)

                                var imageHeight = 1
                                var imageWidth = 1
                                var imageInitialScale = 1.0

                                (painter.state as? AsyncImagePainter.State.Success)?.painter?.let {
                                    imageHeight = it.intrinsicSize.height.toInt()
                                    imageWidth = it.intrinsicSize.width.toInt()
                                    imageInitialScale = widthPix * 1.0 / imageWidth
                                }

                                val shouldScrollY = (heightPix - scale * imageInitialScale * imageHeight) < 0
                                val shouldScrollX = (widthPix - scale * imageWidth * imageWidth) < 0

                                val koefX = (scale - 1) / 2
                                val yMinFilledScale = heightPix / (imageHeight * imageInitialScale)
                                val koefY = (scale - yMinFilledScale) / 2
                                val maxX = widthPix * koefX
                                val maxY = imageHeight * imageInitialScale * koefY

                                if (calculatedX <= maxX && shouldScrollX) {
                                    offsetX.floatValue += offset.x
                                }
                                if (calculatedY < maxY && shouldScrollY) {
                                    offsetY.floatValue += offset.y
                                }

                                scrollState?.run {
                                    coroutineScope.launch {
                                        setScrolling(true)
                                    }
                                }
                            }
                        } while (event.changes.any { it.pressed })
                    }
                }
            }

    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier
                .align(imageAlign)
                .graphicsLayer {
                    if (isZoomable) {
                        scaleX = maxOf(maxScale, minOf(minScale, scale))
                        scaleY = maxOf(maxScale, minOf(minScale, scale))

                        translationX = offsetX.floatValue
                        translationY = offsetY.floatValue
                    }
                }
        )
    }
}

suspend fun ScrollableState.setScrolling(value: Boolean) {
    scroll(scrollPriority = MutatePriority.PreventUserInput) {
        when (value) {
            true -> Unit
            else -> awaitCancellation()
        }
    }
}
