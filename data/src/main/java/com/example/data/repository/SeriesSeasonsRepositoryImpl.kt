package com.example.data.repository

import android.util.Log
import com.example.data.remote.VodApi
import com.example.data.remote.dto.toDomain
import com.example.domain.model.SeasonEpisodes
import com.example.domain.repository.SeriesSeasonsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeriesSeasonsRepositoryImpl(
    private val api: VodApi
) : SeriesSeasonsRepository {

    override suspend fun getSeasons(contentId: String): Result<List<SeasonEpisodes>> {
        return withContext(Dispatchers.IO) {
            try {
                val defaultParams = mapOf(
                    "version" to "1.0",
                    "deviceid" to "test-device",
                    "auid" to "test-auid",
                    "country" to "USA",
                    "user-rating" to "3"
                )

                val response = api.getSeasonsForSeries(defaultParams, contentId)
                Log.d("SEASONS_RAW_JSON", response.toString())
                val domainModel = response.map {it.toDomain()}
                Result.success(domainModel)
            } catch (e: Exception) {
                Log.e("SEASONS_API_ERROR", e.message ?: "Unknown error", e)
                Result.failure(e)
            }
        }
    }
}
