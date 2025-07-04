package com.example.domain.repository

import com.example.domain.model.SeriesMetadata

interface SeriesMetadataRepository {
    suspend fun getSeriesMetadata(contentId: String): Result<SeriesMetadata>
}
