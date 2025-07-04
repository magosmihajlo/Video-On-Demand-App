package com.example.domain.repository

import com.example.domain.model.Auid

interface AuidRepository {
    suspend fun getAuidModel(advertisingId: String): Result<Auid>
}