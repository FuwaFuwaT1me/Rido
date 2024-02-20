package com.example.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LazyColumnOrRow(
    orientation: Orientation,
    arrangement: Arrangement.HorizontalOrVertical,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    if (orientation == Orientation.VERTICAL) {
        LazyColumn(
            verticalArrangement = arrangement,
            modifier = modifier
        ) {
            content()
        }
    } else {
        LazyRow(
            horizontalArrangement = arrangement,
            modifier = modifier
        ) {
            content()
        }
    }
}
