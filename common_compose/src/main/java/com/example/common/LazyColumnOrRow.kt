package com.example.common

import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HorizontalOrVerticalPager(
    pagerState: PagerState,
    orientation: Orientation,
    beyondBoundPageCount: Int,
    flingBehavior: TargetedFlingBehavior,
    userScrollEnabled: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit
) {
    if (orientation == Orientation.VERTICAL) {
        VerticalPager(
            modifier = modifier,
            state = pagerState,
            beyondViewportPageCount = beyondBoundPageCount,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
        ) { page ->
            content(page)
        }
    } else {
        HorizontalPager(
            modifier = modifier,
            state = pagerState,
            beyondViewportPageCount = beyondBoundPageCount,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
        ) { page ->
            content(page)
        }
    }
}
