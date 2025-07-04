package com.example.data.remote.dto

import com.example.domain.model.EpisodeInfo
import com.example.domain.model.SeriesMetadata

data class SeriesMetadataDto(
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
    val episodeInfo: EpisodeInfoDto?
)

data class EpisodeInfoDto(
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

fun SeriesMetadataDto.toDomain(): SeriesMetadata {
    return SeriesMetadata(
        contentId = contentId,
        title = title,
        description = description,
        thumbnail = thumbnail,
        rating = rating,
        origRating = origRating,
        year = year,
        genre = genre,
        language = language,
        type = type,
        trailerUrl = trailerUrl,
        numSeasons = numSeasons,
        cast = cast,
        resumeFromSeason = resumeFromSeason,
        resumeFromEpisode = resumeFromEpisode,
        episodePlaybackUrl = episodePlaybackUrl,
        episodeDurationSec = episodeDurationSec,
        episodeInfo = episodeInfo?.toDomain()
    )
}

fun EpisodeInfoDto.toDomain(): EpisodeInfo {
    return EpisodeInfo(
        durationSec = durationSec,
        contentId = contentId,
        title = title,
        description = description,
        thumbnail = thumbnail,
        rating = rating,
        origRating = origRating,
        year = year,
        genre = genre,
        director = director,
        cast = cast,
        runtime = runtime,
        language = language,
        vodPlaybackUrl = vodPlaybackUrl,
        trailerUrl = trailerUrl,
        season = season,
        episode = episode,
        type = type
    )
}
