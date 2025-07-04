package com.example.presentation.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.navigation.AppScreen
import com.example.presentation.ui.components.ItemDetails
import com.example.presentation.ui.components.PositionFocusedItemInLazyLayout
import com.example.presentation.ui.screen.main.components.TopNavBar
import com.example.presentation.viewmodel.VodUiState
import com.example.presentation.viewmodel.VodViewModel
import androidx.compose.ui.focus.onFocusChanged
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import com.example.presentation.ui.util.rememberIsResumed
import com.example.presentation.ui.util.TvFocusManager
import androidx.compose.ui.focus.FocusManager
import com.example.domain.model.RailItem
import com.example.domain.model.VodItem
import com.example.presentation.ui.components.Rail
import com.example.presentation.ui.components.ItemCard
import com.example.presentation.ui.components.ItemDetailsBar

@Composable
fun VodScreen(
    viewModel: VodViewModel = hiltViewModel(),
    onNavigateToDetails: (AppScreen.Details) -> Unit,
    onNavigateToPlayer: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val localFocusManager = LocalFocusManager.current
    val itemFocusRequesters = remember { mutableStateMapOf<String, FocusRequester>() }
    val isResumed = rememberIsResumed()


    LaunchedEffect(isResumed, uiState.focusedItem?.id) {
        val itemId = uiState.focusedItem?.id ?: return@LaunchedEffect
        TvFocusManager.requestFocusForItem(itemId, itemFocusRequesters)
        TvFocusManager.requestFocusForItem(itemId, itemFocusRequesters)
        delay(5000)
        if (uiState.focusedItem?.id == itemId && uiState.focusedItem?.streamUrl?.isNotBlank() == true) {
            onNavigateToPlayer(uiState.focusedItem!!.streamUrl)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        when {
            uiState.isLoading -> VodLoadingContent()
            uiState.error != null -> VodErrorContent(uiState.error)
            else -> VodMainContent(
                uiState = uiState,
                isResumed = isResumed,
                localFocusManager = localFocusManager,
                itemFocusRequesters = itemFocusRequesters,
                onItemFocused = viewModel::updateFocusedItem,
                onItemClicked = { item ->
                    viewModel.setSelectedItem(item)
                    onNavigateToDetails(AppScreen.Details(item.id))
                }
            )
        }
    }
}

//region Loading & Error

@Composable
fun VodLoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun VodErrorContent(error: Throwable?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Error: ${error?.localizedMessage ?: "Unknown error"}",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
    }
}


//endregion

//region Main Content


@Composable
fun VodMainContent(
    uiState: VodUiState,
    isResumed: Boolean,
    localFocusManager: FocusManager,
    itemFocusRequesters: MutableMap<String, FocusRequester>,
    onItemFocused: (VodItem?) -> Unit,
    onItemClicked: (VodItem) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopNavBarWithFocusReset(
            isResumed = isResumed,
            onResetFocus = { onItemFocused.invoke(null) },
            onOnDemandClick = { localFocusManager.moveFocus(FocusDirection.Down) }
        )

        ItemDetailsBar(
            item = uiState.focusedItem,
            height = 70.dp,
            padding = PaddingValues(horizontal = 16.dp, vertical = 2.dp)
        ) { currentItem ->
            ItemDetails(item = currentItem)
        }

        RailsList(
            rails = uiState.rails,
            focusRequesters = itemFocusRequesters,
            onItemFocused = onItemFocused,
            onItemClicked = onItemClicked,
        )
    }
}

//endregion

//region Top Navigation

@Composable
fun TopNavBarWithFocusReset(
    isResumed: Boolean,
    onResetFocus: () -> Unit,
    onOnDemandClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.hasFocus && isResumed) {
                    onResetFocus()
                }
            }
    ) {
        TopNavBar(onOnDemandClick = onOnDemandClick)
    }
}

//endregion

//region Rails List

@Composable
fun RailsList(
    rails: List<RailItem>,
    focusRequesters: MutableMap<String, FocusRequester>,
    onItemFocused: (VodItem) -> Unit,
    onItemClicked: (VodItem) -> Unit
) {
    if (rails.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "No VOD content available at the moment.",
                modifier = Modifier.padding(16.dp)
            )
        }
    } else {
        PositionFocusedItemInLazyLayout(
            parentFraction = 0.05f,
            childFraction = 0f,
            additionalFocusPadding = 16.dp
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
            ) {
                items(rails, key = { it.title }) { rail ->
                    Rail(
                        title = rail.title,
                        items = rail.items,
                        focusRequesters = focusRequesters,
                        itemKey = { it.id },
                        onItemFocused = onItemFocused,
                        onItemClicked = onItemClicked
                    ) { item, modifier, onClick ->
                        ItemCard(
                            item = item,
                            modifier = modifier,
                            onFocus = { onItemFocused(item) },
                            onClick = onClick,
                            cardWidth = null,
                            focusedScale = 1.15f,
                            focusedBorderWidth = 1.5.dp,
                            containerColor = Color.Unspecified,
                            focusedContainerColor = Color.Unspecified
                        )
                    }

                }
            }
        }
    }
}

//endregion