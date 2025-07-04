package com.example.data.remote.dto

import com.example.domain.model.SeasonEpisodeItem
import com.example.domain.model.SeasonEpisodes

data class SeasonDto(
    val season: String,
    val episodes: List<EpisodeDto>
)

data class EpisodeDto(
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

fun SeasonDto.toDomain(): SeasonEpisodes {
    val seasonNumberInt = season.toIntOrNull() ?: 0
    return SeasonEpisodes(
        seasonNumber = seasonNumberInt,
        episodes = episodes.map { it.toDomain(seasonNumberInt) }
    )
}

fun EpisodeDto.toDomain(seasonNumber: Int): SeasonEpisodeItem {
    return SeasonEpisodeItem(
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
        season = seasonNumber.toString(),
        episode = episode,
        type = type
    )
}
