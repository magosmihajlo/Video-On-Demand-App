package com.example.presentation.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun <T> ItemDetailsBar(
    item: T?,
    modifier: Modifier = Modifier,
    height: Dp,
    padding: PaddingValues,
    content: @Composable (T) -> Unit
) where T : Any {
    AnimatedVisibility(
        visible = item != null,
        enter = slideInVertically { -it } + fadeIn(),
        exit = slideOutVertically { -it } + shrinkVertically() + fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .padding(padding)
        ) {
            AnimatedContent(
                targetState = item,
                modifier = Modifier.fillMaxSize(),
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ItemDetailsBarContent"
            ) { currentItem ->
                if (currentItem != null) {
                    content(currentItem)
                } else {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}