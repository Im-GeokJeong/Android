package com.im_geokjeong.network

import com.im_geokjeong.BuildConfig.BASE_URL
import com.im_geokjeong.model.Office
import com.im_geokjeong.model.OfficeDetailResponse
import com.im_geokjeong.model.OfficeResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {
    @GET("office/list")
    suspend fun getOffices(): OfficeResponse

    @GET("office/{officeId}")
    suspend fun getOfficeDetail(@Path("officeId") officeId: Int): OfficeDetailResponse


    companion object{

        private const val baseUrl = BASE_URL

        fun create(): ApiClient{
            val logger = HttpLoggingInterceptor().apply{
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient::class.java)
        }
    }
}