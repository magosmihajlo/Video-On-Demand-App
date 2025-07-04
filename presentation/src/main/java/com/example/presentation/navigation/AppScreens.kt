package com.example.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppScreen {

    @Serializable
    data object Vod : AppScreen

    @Serializable
    data class Details(val itemId: String) : AppScreen

    @Serializable
    data class Player(val streamUrl: String) : AppScreen
}
