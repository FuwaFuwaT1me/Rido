package com.example.common

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val SNAPPING_OFFSET = 150

@Composable
fun LazyColumnOrRow(
    listState: LazyListState,
    orientation: Orientation,
    arrangement: Arrangement.HorizontalOrVertical,
    flingBehavior: FlingBehavior,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    if (orientation == Orientation.VERTICAL) {
        LazyColumn(
            state = listState,
            verticalArrangement = arrangement,
            flingBehavior = flingBehavior,
            modifier = modifier
        ) {
            content()
        }
    } else {
        LazyRow(
            state = listState,
            horizontalArrangement = arrangement,
            flingBehavior = flingBehavior,
            modifier = modifier
        ) {
            content()
        }
    }
}

fun LazyListState.snap(scope: CoroutineScope, prevVisibleIndex: Int) {

    if (layoutInfo.visibleItemsInfo.size < 2) return

    val first = layoutInfo.visibleItemsInfo[0]
    val second = layoutInfo.visibleItemsInfo[1]

    val direction = if (firstVisibleItemIndex < prevVisibleIndex) {
        Direction.LEFT
    } else if (prevVisibleIndex < second.index) {
        Direction.RIGHT
    } else {
        null
    }

    //TODO : calculate snapping speed by remaining space
    when {
        first.offset < -SNAPPING_OFFSET && direction == Direction.RIGHT -> {
            scope.launch {
                animateScrollBy(second.offset.toFloat(), tween(500, easing = FastOutLinearInEasing))
            }
        }

        second.offset > SNAPPING_OFFSET && direction == Direction.LEFT -> {
            scope.launch {
                animateScrollBy(first.offset.toFloat(), tween(500, easing = FastOutLinearInEasing))
            }
        }

        first.offset in (-SNAPPING_OFFSET..SNAPPING_OFFSET) -> {
            scope.launch {
                animateScrollBy(first.offset.toFloat(), tween(200))
            }
        }

        second.offset in (-SNAPPING_OFFSET..SNAPPING_OFFSET) -> {
            scope.launch {
                animateScrollBy(second.offset.toFloat(), tween(200))
            }
        }
    }
}
