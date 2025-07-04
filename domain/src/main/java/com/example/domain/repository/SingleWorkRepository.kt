package com.example.domain.repository

import com.example.domain.model.SingleWork

interface SingleWorkRepository {
    suspend fun getSingleWorkModel(contentId: String): Result<SingleWork>
}
