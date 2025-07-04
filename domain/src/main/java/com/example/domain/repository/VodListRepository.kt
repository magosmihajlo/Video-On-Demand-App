package com.example.domain.repository

import com.example.domain.model.RailItem

interface VodListRepository {
    suspend fun getVodListModel(): Result<List<RailItem>>
}