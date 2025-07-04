package com.example.data.remote

import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.data.remote.dto.AuidDto
import com.example.data.remote.dto.VodDtoDeserializer
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private var apiKey: String? = null

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(AuidDto::class.java, VodDtoDeserializer())
        .setLenient()
        .create()

    private lateinit var client: OkHttpClient
    private lateinit var retrofit: Retrofit

    val api: VodApi
        get() {
            if (!::retrofit.isInitialized) {
                throw IllegalStateException("Retrofit not initialized. Call setApiKey(...) first.")
            }
            return retrofit.create(VodApi::class.java)
        }

    @OptIn(UnstableApi::class)
    fun setApiKey(key: String) {
        if (key.isBlank()) {
            throw IllegalArgumentException("API key must not be blank")
        }
        apiKey = key
        client = provideOkHttpClient(key)
        retrofit = provideRetrofit(client)
        Log.d("RetrofitInstance", "Retrofit initialized with API key.")
    }

    private fun provideOkHttpClient(apiKey: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
        @OptIn(UnstableApi::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            Log.d("ApiKeyInterceptor", "Sending API Key: $apiKey")
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("x-api-key", apiKey)
                .build()
            return chain.proceed(newRequest)
        }
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://stream-stage.aistrm.net:5200")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
