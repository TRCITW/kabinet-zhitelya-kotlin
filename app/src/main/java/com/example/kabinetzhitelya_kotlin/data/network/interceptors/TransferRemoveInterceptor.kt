package com.example.kabinetzhitelya_kotlin.data.network.interceptors

import com.example.kabinetzhitelya_kotlin.data.repositories.AuthRepository
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Okio

class TransferRemoveInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val modRequest = chain.request().newBuilder()
//            .addHeader("Content-Length", (originalResponse.body?.bytes()?.size ?: 0).toString())
            .addHeader("Accept-Encoding", "")
            .build()
//        ResponseBody.create(body?.contentType(), body?.bytes()?.size, Okio.buffer())
        return chain.proceed(modRequest)
    }

}