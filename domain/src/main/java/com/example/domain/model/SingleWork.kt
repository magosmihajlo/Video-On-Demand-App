package com.example.domain.model

data class SingleWork(
    val durationSec: Int,
    val contentId: String,
    val title: String,
    val description: String,
    val thumbnail: String?,
    val rating: String?,
    val origRating: String?,
    val year: String?,
    val genre: String?,
    val director: String?,
    val cast: String?,
    val runtime: String?,
    val language: String?,
    val streamUrl: String,
    val trailerUrl: String?,
    val type: String?,
    val licenseServerUrl: String?
)
