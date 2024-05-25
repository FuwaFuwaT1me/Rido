package com.example.common

import android.util.Log
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

                            val prevScale = scale

                            scale = if (scale * calculatedZoom in 1f..3f) {
                                scale * calculatedZoom
                            } else {
                                scale
                            }

                            var imageHeight = 1
                            var imageWidth = 1
                            var imageInitialScale = 1f

                            (painter.state as? AsyncImagePainter.State.Success)?.painter?.let {
                                imageHeight = it.intrinsicSize.height.toInt()
                                imageWidth = it.intrinsicSize.width.toInt()
                                imageInitialScale = widthPix * 1f / imageWidth
                            }

                            val shouldScrollY = (heightPix - scale * imageInitialScale * imageHeight) < 0
                            val shouldScrollX = (widthPix - scale * imageWidth * imageWidth) < 0

                            val offset = event.calculatePan()
                            val x = offsetX.floatValue + offset.x
                            val y = offsetY.floatValue + offset.y
                            val calculatedX = abs(x)
                            val calculatedY = abs(y)

                            val koefX = (scale - 1) / 2f
                            val yMinFilledScale = heightPix / (imageHeight * imageInitialScale)
                            val koefY = (scale - yMinFilledScale) / 2f
                            val maxX = widthPix * koefX
                            val maxY = imageHeight * imageInitialScale * koefY

                            if (scale != prevScale) {
                                if (!shouldScrollX) {
                                    offsetX.floatValue = 0f
                                } else if (calculatedX >= maxX) {
                                    if (x < 0) {
                                        offsetX.floatValue = -maxX
                                    } else {
                                        offsetX.floatValue = maxX
                                    }
                                }
                                if (!shouldScrollY) {
                                    offsetY.floatValue = 0f
                                } else if (calculatedY >= maxY) {
                                    if (y < 0) {
                                        offsetY.floatValue = -maxY
                                    } else {
                                        offsetY.floatValue = maxY
                                    }
                                }
                            }

                            if (scale in 1f..3f) {
                                scrollState?.run {
                                    coroutineScope.launch {
                                        setScrolling(false)
                                    }
                                }

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

//                                Log.d("ANIME", "offssetX = ${offsetX.floatValue}, offsetY = ${offsetY.floatValue}")
                                Log.d("ANIME", "x = ${x}, y = ${y}")
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
                    Log.d("BRUH", "${this.transformOrigin.pivotFractionX}")
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
