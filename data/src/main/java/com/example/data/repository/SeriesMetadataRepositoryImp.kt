package com.example.data.repository

import android.util.Log
import com.example.data.remote.VodApi
import com.example.data.remote.dto.toDomain
import com.example.domain.model.SeriesMetadata
import com.example.domain.repository.SeriesMetadataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeriesMetadataRepositoryImpl(
    private val api: VodApi
) : SeriesMetadataRepository {

    override suspend fun getSeriesMetadata(contentId: String): Result<SeriesMetadata> {
        return withContext(Dispatchers.IO) {
            try {
                val defaultParams = mapOf(
                    "version" to "1.0",
                    "deviceid" to "test-device",
                    "auid" to "test-auid",
                    "country" to "USA",
                    "user-rating" to "3"
                )

                val response = api.getSeriesMetadata(defaultParams, contentId)
                Log.d("SERIES_METADATA_RAW", "Raw: $response")

                val domainModel = response.toDomain()
                Log.d("SERIES_METADATA_MAPPED", "Mapped: $domainModel")
                Result.success(domainModel)
            } catch (e: Exception) {
                Log.e("SERIES_METADATA_ERROR", "Exception: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
}
