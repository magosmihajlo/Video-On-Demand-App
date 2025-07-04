package com.example.data.remote.dto

import com.example.domain.model.VodItem
import com.google.gson.annotations.SerializedName
import android.util.Log

data class VodItemDto(
    @SerializedName("durationSec") val durationSec: Int?,
    @SerializedName("contentId") val contentId: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("rating") val rating: String?,
    @SerializedName("origRating") val origRating: String?,
    @SerializedName("year") val year: String?,
    @SerializedName("genre") val genre: String?,
    @SerializedName("cast") val cast: String?,
    @SerializedName("runtime") val runtime: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("trailerUrl") val trailerUrl: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("numSeasons") val numSeasons: Int?
)

fun VodItemDto.toDomain(): VodItem? {
    if (contentId.isNullOrBlank()) {
        Log.w("DtoMapping", "Skipping item due to blank contentId: $this")
        return null
    }
    if (title.isNullOrBlank()) {
        Log.w("DtoMapping", "Skipping item due to blank title: ID=$contentId")
        return null
    }
    if (type.isNullOrBlank()) {
        Log.w("DtoMapping", "Skipping item due to blank type: ID=$contentId")
        return null
    }

    val formattedRuntime = durationSec?.let { seconds ->
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m"
    } ?: runtime

    return VodItem(
        id = contentId,
        title = title,
        description = description ?: "",
        streamUrl = trailerUrl ?: "",
        thumbnail = thumbnail,
        type = type,
        year = year.orEmpty(),
        runtime = formattedRuntime.orEmpty(),
        rating = rating.orEmpty(),
        genre = genre.orEmpty(),
        cast = cast.orEmpty(),
        language = language.orEmpty(),
        origRating = origRating,
        numSeasons = numSeasons
    )
}
