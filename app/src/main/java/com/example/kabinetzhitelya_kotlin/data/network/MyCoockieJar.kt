package com.example.kabinetzhitelya_kotlin.data.network

import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class MyCookieJar: CookieJar {

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        Log.d("test", "Here is cookie ${url}")
        return emptyList<Cookie>()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        Log.d("test", "Here is cookie ${cookies}")
    }

}