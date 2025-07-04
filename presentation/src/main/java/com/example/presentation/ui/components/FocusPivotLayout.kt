package com.example.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.BringIntoViewSpec
import androidx.compose.foundation.gestures.LocalBringIntoViewSpec
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PositionFocusedItemInLazyLayout(
    parentFraction: Float = 0f,
    childFraction: Float = 0f,
    focusedItemScaleFactor: Float = 1.1f,
    additionalFocusPadding: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val additionalFocusPaddingPx = remember(additionalFocusPadding, density) {
        with(density) { additionalFocusPadding.toPx() }
    }

    val bringIntoViewSpec = remember(parentFraction, childFraction, focusedItemScaleFactor, additionalFocusPaddingPx) {
        object : BringIntoViewSpec {
            override fun calculateScrollDistance(
                offset: Float,
                size: Float,
                containerSize: Float
            ): Float {

                val unscaledItemAnchorPointTargetPx = containerSize * parentFraction - size * childFraction

                val leadingEdgeShiftDueToScalePx = (size * (focusedItemScaleFactor - 1f)) / 2f

                val finalTargetForUnscaledItemAnchorPx =
                    unscaledItemAnchorPointTargetPx + additionalFocusPaddingPx + leadingEdgeShiftDueToScalePx

                return offset - finalTargetForUnscaledItemAnchorPx
            }
        }
    }
    CompositionLocalProvider(
        LocalBringIntoViewSpec provides bringIntoViewSpec,
        content = content,
    )
}