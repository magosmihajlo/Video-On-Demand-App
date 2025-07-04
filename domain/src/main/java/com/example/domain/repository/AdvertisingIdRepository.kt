package com.example.domain.repository

interface AdvertisingIdRepository {
    suspend fun getAdvertisingId(): Result<String>
}