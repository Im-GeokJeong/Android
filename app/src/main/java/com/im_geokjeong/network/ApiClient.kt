package com.im_geokjeong.network

import com.im_geokjeong.BuildConfig.BASE_URL
import com.im_geokjeong.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiClient {
    @GET("office/list")
    suspend fun getOffices(): OfficeResponse

    @GET("post/all")
    suspend fun getArticle(): PersonResponse

    @GET("post/{postId}")
    suspend fun getArticleDetail(@Path("postId") postId: Int): PersonDetailResponse

    @POST("post")
    suspend fun postArticle(@Body person: PostPerson): PostResponse

    @GET("office/{officeId}")
    suspend fun getOfficeDetail(@Path("officeId") officeId: Int): OfficeDetailResponse

    companion object {
        private const val baseUrl = BASE_URL

        fun create(): ApiClient {
            val logger = HttpLoggingInterceptor().apply {
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