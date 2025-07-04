package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.domain.repository.AdvertisingIdRepository
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AdvertisingIdRepositoryImpl(private val context: Context) : AdvertisingIdRepository {

    override suspend fun getAdvertisingId(): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val appContext = context.applicationContext
                val info = AdvertisingIdClient.getAdvertisingIdInfo(appContext)
                val id = info.id
                return@withContext if (!id.isNullOrEmpty()) {
                    Log.d("AdvertisingIdRepo", "Fetched Advertising ID: $id")
                    Result.success(id)
                } else {
                    Result.failure(Exception("Fetched Advertising ID was null or empty."))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}