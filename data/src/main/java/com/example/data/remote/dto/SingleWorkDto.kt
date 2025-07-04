package com.example.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.example.domain.model.SingleWork

data class SingleWorkDto(
    @SerializedName("durationSec") val durationSec: Int? = null,
    @SerializedName("contentId") val contentId: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
    @SerializedName("rating") val rating: String? = null,
    @SerializedName("origRating") val origRating: String? = null,
    @SerializedName("year") val year: String? = null,
    @SerializedName("genre") val genre: String? = null,
    @SerializedName("director") val director: String? = null,
    @SerializedName("cast") val cast: String? = null,
    @SerializedName("runtime") val runtime: String? = null,
    @SerializedName("language") val language: String? = null,
    @SerializedName("vodPlaybackUrl") val vodPlaybackUrl: String? = null,
    @SerializedName("trailerUrl") val trailerUrl: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("licenseServerUrl") val licenseServerUrl: String? = null
)

fun SingleWorkDto.toDomain(): SingleWork {
    return SingleWork(
        durationSec = durationSec ?: 0,
        contentId = contentId ?: throw IllegalArgumentException("contentId is null"),
        title = title ?: "",
        description = description ?: "",
        thumbnail = thumbnail,
        rating = rating,
        origRating = origRating,
        year = year,
        genre = genre,
        director = director,
        cast = cast,
        runtime = runtime,
        language = language,
        streamUrl = vodPlaybackUrl ?: throw IllegalArgumentException("vodPlaybackUrl is null"),
        trailerUrl = trailerUrl,
        type = type,
        licenseServerUrl = licenseServerUrl
    )
}
