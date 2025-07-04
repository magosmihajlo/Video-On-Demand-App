package com.example.data.repository

import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.data.local.AuidDataStore
import com.example.data.remote.RetrofitInstance
import com.example.data.remote.dto.mapToDomain
import com.example.domain.model.Auid
import com.example.domain.repository.AuidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuidRepositoryImpl(
    private val auidDataStore: AuidDataStore
) : AuidRepository {

    @OptIn(UnstableApi::class)
    override suspend fun getAuidModel(advertisingId: String): Result<Auid> {
        return withContext(Dispatchers.IO) {
            try {
                val cached = auidDataStore.getAuidDatabase()
                if (cached != null) {
                    Log.d("AUID_TEST", "Loaded AUID from DataStore: $cached")
                    return@withContext Result.success(Auid(cached))
                }

                if (advertisingId.isBlank()) {
                    Log.d("AUID_TEST", "Advertising ID is blank.")
                    return@withContext Result.failure(Exception("Empty Advertising ID"))
                }

                val response = RetrofitInstance.api.getAuidServer(advertisingId)
                if (response.isSuccessful) {
                    response.body()?.let { dto ->
                        val domain = dto.mapToDomain()
                        Log.d("AUID_TEST", "Fetched AUID from API: ${domain.value}")
                        auidDataStore.saveAuid(domain.value)
                        return@withContext Result.success(domain)
                    } ?: run {
                        Log.d("AUID_TEST", "API response body was null")
                        return@withContext Result.failure(Exception("Null body"))
                    }
                } else {
                    Log.d("AUID_TEST", "API error: ${response.code()}")
                    return@withContext Result.failure(Exception("API error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("AUID_TEST", "Exception in AUID fetch: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
}
