package com.example.kabinetzhitelya_kotlin.data.network

import com.example.kabinetzhitelya_kotlin.BuildConfig
import com.example.kabinetzhitelya_kotlin.data.network.interceptors.CookieInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://lk2.eis24.me/api/v4/"
    private val contentType = "application/json".toMediaType()
    private val jsonFormat = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        allowSpecialFloatingPointValues = true
        isLenient = true
    }

    private val jsonConverterFactory by lazy {
        jsonFormat.asConverterFactory(contentType)
    }

    val endpointsInterface = buildEndpointsInterfaceAPI(BASE_URL)

    private fun buildEndpointsInterfaceAPI(baseURL: String): EndpointsInterface {
        val okHttpClient = provideOkHttpClientBuilder().apply {
//            addInterceptor(XRequestedHeaderInterceptor())
//            addInterceptor(AuthTokenInterceptor())
            addInterceptor(CookieInterceptor())
            addLoggingInterceptorIfNeeded()
        }
            .cookieJar(MyCookieJar())
            .build()
        val client = provideRetrofitClient(okHttpClient, baseURL)
        return client.create(EndpointsInterface::class.java)
    }

    private fun provideOkHttpClientBuilder() = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
    }

    private fun OkHttpClient.Builder.addLoggingInterceptorIfNeeded() {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)
        }
    }

    private fun provideRetrofitClient(okHttpClient: OkHttpClient, baseURL: String) = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}