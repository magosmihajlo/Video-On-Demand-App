package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.presentation.ui.screen.main.VodScreen
import com.example.presentation.ui.screen.details.SeriesDetails
import com.example.presentation.ui.screen.details.SingleWorkDetails
import com.example.presentation.ui.screen.player.PlayerScreen
import com.example.presentation.viewmodel.VodViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.VodContentType

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
) {
    val vodViewModel: VodViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreen.Vod
    ) {
        composable<AppScreen.Vod> {
            VodScreen(
                viewModel = vodViewModel,
                onNavigateToDetails = { detailsRoute ->
                    navController.navigate(detailsRoute) {
                        popUpTo(AppScreen.Vod) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavigateToPlayer = { streamUrl ->
                    navController.navigate(AppScreen.Player(streamUrl))
                }
            )
        }

        composable<AppScreen.Details> { _ ->
            val selectedItemState = vodViewModel.selectedItem.collectAsStateWithLifecycle()
            val selectedItem = selectedItemState.value

            selectedItem?.let { item ->
                when (item.contentType) {
                    is VodContentType.Series -> {
                        SeriesDetails(
                            item = item,
                            onBackPressed = {
                                vodViewModel.setSelectedItem(null)
                                navController.popBackStack()
                            },
                            onWatchPressed = { streamUrl ->
                                navController.navigate(AppScreen.Player(streamUrl))
                            }
                        )
                    }

                    is VodContentType.SingleWork -> {
                        SingleWorkDetails(
                            item = item,
                            onBackPressed = {
                                vodViewModel.setSelectedItem(null)
                                navController.popBackStack()
                            },
                            onWatchPressed = { streamUrl ->
                                navController.navigate(AppScreen.Player(streamUrl))
                            }
                        )
                    }
                }
            } ?: run {
                navController.popBackStack()
            }
        }

        composable<AppScreen.Player> { backStackEntry ->
            val args = backStackEntry.toRoute<AppScreen.Player>()
            PlayerScreen(
                streamUrl = args.streamUrl,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
