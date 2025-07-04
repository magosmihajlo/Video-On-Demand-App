package com.example.data.remote

import com.example.data.remote.dto.AuidDto
import com.example.data.remote.dto.RailItemDto
import com.example.data.remote.dto.SeasonDto
import com.example.data.remote.dto.SeriesMetadataDto
import com.example.data.remote.dto.SingleWorkDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface VodApi {

    @GET("/v3/auid")
    suspend fun getAuidServer(@Query("deviceId") deviceId: String): Response<AuidDto>

    @GET("/v3/vodlist")
    suspend fun getVodListServer(
        @Query("version") version: String,
        @Query("deviceid") deviceId: String,
        @Query("auid") auid: String,
        @Query("country") country: String,
        @Query("user-rating") userRating: Int
    ): Response<List<RailItemDto>>

    @GET("/v3/vod/single-work")
    suspend fun getSingleWorkServer(
        @QueryMap(encoded = true) defaultQueries: Map<String, String>,
        @Query("contentId") contentId: String
    ): Response<SingleWorkDto>

    @GET("/v3/vod/series-metadata")
    suspend fun getSeriesMetadata(
        @QueryMap(encoded = true) defaultQueries: Map<String, String>,
        @Query("contentId") contentId: String
    ): SeriesMetadataDto

    @GET("v3/vod/series")
    suspend fun getSeasonsForSeries(
        @QueryMap(encoded = true) defaultQueries: Map<String, String>,
        @Query("contentId") contentId: String
    ): List<SeasonDto>
}
