package com.example.domain.repository

import com.example.domain.model.SeasonEpisodes
interface SeriesSeasonsRepository {
    suspend fun getSeasons(contentId: String): Result<List<SeasonEpisodes>>
}
