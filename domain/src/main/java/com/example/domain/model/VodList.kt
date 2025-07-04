package com.example.domain.model

data class RailItem(
    val title: String,
    val items: List<VodItem>
)

data class VodItem(
    val id: String,
    val title: String,
    val description: String,
    val streamUrl: String,
    val thumbnail: String?,
    val type: String,
    val year: String?,
    val runtime: String?,
    val rating: String?,
    val genre: String?,
    val cast: String?,
    val language: String?,
    val origRating: String?,
    val numSeasons: Int? = null
) {
    val contentType: VodContentType
        get() = if (type == "series") VodContentType.Series else VodContentType.SingleWork
}

sealed class VodContentType {
    data object SingleWork : VodContentType()
    data object Series : VodContentType()
}

