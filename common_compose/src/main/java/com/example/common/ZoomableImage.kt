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
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
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

    var scale by remember { mutableFloatStateOf(1f) }
    val offsetX = remember { mutableFloatStateOf(1f) }
    val offsetY = remember { mutableFloatStateOf(1f) }

    val displayMetrics = LocalContext.current.resources.displayMetrics
    val screenWidthPx = displayMetrics.widthPixels
    val screenHeightPx = displayMetrics.heightPixels
    val screenAspectRatio = screenWidthPx * 1f / screenHeightPx

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
                            val offset = event.calculatePan()

                            val (updatedScale, wasScaleChanged) = if (scale * calculatedZoom in 1f..3f) {
                                scale * calculatedZoom to true
                            } else {
                                scale to false
                            }
                            scale = updatedScale

                            var imageHeight = 1
                            var imageWidth = 1
                            var imageAspectRatio = 1f
                            var imageInitialScale = 1f

                            (painter.state as? AsyncImagePainter.State.Success)?.painter?.let {
                                imageHeight = it.intrinsicSize.height.toInt()
                                imageWidth = it.intrinsicSize.width.toInt()
                                imageAspectRatio = imageWidth * 1f / imageHeight

                                imageInitialScale = if (imageAspectRatio >= screenAspectRatio) {
                                    screenWidthPx * 1f / imageWidth
                                } else {
                                    screenHeightPx * 1f / imageHeight
                                }
                            }

                            val imageHeightFillScreenKoef = screenHeightPx - updatedScale * imageInitialScale * imageHeight
                            val imageWidthFillScreenKoef = screenWidthPx - updatedScale * imageInitialScale * imageWidth
                            val shouldScrollY = imageHeightFillScreenKoef < 0
                            val shouldScrollX = imageWidthFillScreenKoef < 0

                            val x = offsetX.floatValue + offset.x
                            val y = offsetY.floatValue + offset.y
                            val absX = abs(x)
                            val absY = abs(y)

                            val maxX = run {
                                if (imageAspectRatio >= screenAspectRatio) {
                                    val koefX = (updatedScale - 1) / 2f
                                    screenWidthPx * koefX
                                } else {
                                    // percentage of image width on screen
                                    val xMinFilledScale = screenWidthPx / (imageWidth * imageInitialScale)
                                    val koefX = (updatedScale - xMinFilledScale) / 2f
                                    imageWidth * imageInitialScale * koefX
                                }
                            }

                            val maxY = run {
                                if (imageAspectRatio >= screenAspectRatio) {
                                    // percentage of image height on screen
                                    val yMinFilledScale = screenHeightPx / (imageHeight * imageInitialScale)
                                    val koefY = (updatedScale - yMinFilledScale) / 2f
                                    imageHeight * imageInitialScale * koefY
                                } else {
                                    val koefY = (updatedScale - 1) / 2f
                                    screenHeightPx * koefY
                                }
                            }

                            // define max borders while zooming
                            if (wasScaleChanged) {
                                if (!shouldScrollX) {
                                    offsetX.floatValue = 0f
                                } else if (absX >= maxX) {
                                    if (x < 0) {
                                        offsetX.floatValue = -maxX
                                    } else {
                                        offsetX.floatValue = maxX
                                    }
                                }
                                if (!shouldScrollY) {
                                    offsetY.floatValue = 0f
                                } else if (absY >= maxY) {
                                    if (y < 0) {
                                        offsetY.floatValue = -maxY
                                    } else {
                                        offsetY.floatValue = maxY
                                    }
                                }
                            }

                            // define max borders when scrolling
                            if (updatedScale in 1f..3f) {
                                scrollState?.run {
                                    coroutineScope.launch {
                                        setScrolling(false)
                                    }
                                }

                                if (absX <= maxX && shouldScrollX) {
                                    offsetX.floatValue += offset.x
                                }
                                if (absY < maxY && shouldScrollY) {
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
                }.onGloballyPositioned {
                    it.boundsInParent()
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
