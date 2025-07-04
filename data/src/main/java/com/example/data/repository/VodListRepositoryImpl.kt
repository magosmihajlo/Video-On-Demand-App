package com.example.data.repository

import android.util.Log
import com.example.data.remote.RetrofitInstance
import com.example.data.remote.dto.toDomain
import com.example.domain.model.RailItem
import com.example.domain.repository.AdvertisingIdRepository
import com.example.domain.repository.AuidRepository
import com.example.domain.repository.VodListRepository


class VodListRepositoryImpl(
    private val auidRepository: AuidRepository,
    private val advertisingIdRepository: AdvertisingIdRepository
) : VodListRepository {

    override suspend fun getVodListModel(): Result<List<RailItem>> {
        return try {
            val advertisingId = advertisingIdRepository.getAdvertisingId().getOrThrow()
            val auid = auidRepository.getAuidModel(advertisingId).getOrThrow()

            Log.d("VodListRepositoryImpl", "Calling getVodList API with AUID=${auid.value} and AdID=$advertisingId")

            val response = RetrofitInstance.api.getVodListServer(
                version = "v3",
                deviceId = advertisingId,
                auid = auid.value,
                country = "USA",
                userRating = 3
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toDomain())
                } else {
                    Result.failure(Exception("Response body null"))
                }
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("VodListRepositoryImpl", "Error fetching VOD list", e)
            Result.failure(e)
        }
    }
}
