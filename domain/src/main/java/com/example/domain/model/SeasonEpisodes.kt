package com.example.domain.model

data class SeasonEpisodes(
    val seasonNumber: Int,
    val episodes: List<SeasonEpisodeItem>
)

data class SeasonEpisodeItem(
    val durationSec: Int,
    val contentId: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val rating: String,
    val origRating: String,
    val year: String,
    val genre: String,
    val director: String?,
    val cast: String?,
    val runtime: String?,
    val language: String,
    val vodPlaybackUrl: String?,
    val trailerUrl: String?,
    val season: String?,
    val episode: String?,
    val type: String
)
