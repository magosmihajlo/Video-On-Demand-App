package com.example.domain.model

data class SeriesMetadata(
    val contentId: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val rating: String,
    val origRating: String,
    val year: String,
    val genre: String,
    val language: String,
    val type: String,
    val trailerUrl: String?,
    val numSeasons: Int,
    val cast: String?,
    val resumeFromSeason: String?,
    val resumeFromEpisode: String?,
    val episodePlaybackUrl: String?,
    val episodeDurationSec: Int?,
    val episodeInfo: EpisodeInfo?
)

data class EpisodeInfo(
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
