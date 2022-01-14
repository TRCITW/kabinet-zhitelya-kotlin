package com.example.kabinetzhitelya_kotlin.data.network.interceptors

import android.util.Log
import com.example.kabinetzhitelya_kotlin.data.repositories.AuthRepository
import okhttp3.Cookie
import okhttp3.Interceptor
import okhttp3.Response

class CookieInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val cookie = originalResponse.headers("Set-Cookie")
        Log.d("test", "${cookie}")
        AuthRepository.saveCookie(cookie.first())
        return originalResponse
    }

}