package com.example.data.repository

import android.util.Log
import com.example.data.remote.VodApi
import com.example.data.remote.dto.toDomain
import com.example.domain.model.SingleWork
import com.example.domain.repository.SingleWorkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SingleWorkRepositoryImpl(
    private val api: VodApi
) : SingleWorkRepository {

    override suspend fun getSingleWorkModel(contentId: String): Result<SingleWork> {
        return withContext(Dispatchers.IO) {
            try {
                val defaultParams = mapOf(
                    "version" to "1.0",
                    "deviceid" to "test-device",
                    "auid" to "test-auid",
                    "country" to "USA",
                    "user-rating" to "3"
                )

                val response = api.getSingleWorkServer(defaultParams, contentId)

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("TEST_SINGLE_WORK", "Mapped DTO: $body")

                    body?.let {
                        val domain = it.toDomain()
                        Log.d("TEST_SINGLE_WORK", "Success: $domain")
                        Result.success(domain)
                    } ?: Result.failure(Exception("Null body"))

                } else {
                    val errorJson = response.errorBody()?.string()
                    Log.d("SERVER_RAW_RESPONSE", "Raw JSON Error: $errorJson")
                    Log.e("TEST_SINGLE_WORK", "Server error ${response.code()}: ${response.message()}")
                    Result.failure(Exception("API error ${response.code()} - ${response.message()}"))
                }

            } catch (e: Exception) {
                Log.e("SINGLE_WORK_EXCEPTION", "Exception: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
}
