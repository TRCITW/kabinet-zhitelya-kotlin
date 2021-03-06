package com.example.kabinetzhitelya_kotlin.data.repositories

import androidx.core.content.edit
import com.example.kabinetzhitelya_kotlin.data.PreferenseStorage
import com.example.kabinetzhitelya_kotlin.data.network.RetrofitClient
import com.example.kabinetzhitelya_kotlin.data.network.models.*
import io.reactivex.Completable
import io.reactivex.Flowable
import kotlinx.serialization.SerialName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthRepository {

    private val retrofitClient = RetrofitClient.endpointsInterface
    private const val PREFS_TOKEN = "token"
    private const val PREFS_COOKIES = "cookies"
    private const val FCM_TOKEN = "fcm_token"
    private val authPrefs = PreferenseStorage.authPrefs

    fun login(username: String, password: String): Flowable<SuccessAuthResponse> {
        val fcmToken: String? = authPrefs.getString(FCM_TOKEN, null)
        val dto = LoginUserDTO(username, password, fcmToken)

        return retrofitClient.login(dto)
            .toFlowable()
    }

    fun register(number: String, lastName: String, email: String): Flowable<ErrorResponse> {
        val fcmToken: String = authPrefs.getString(FCM_TOKEN, "").toString()
        val dto = RegisterUserDTO(number, lastName, email, fcmToken)
//        val res = retrofitClient.register(dto).enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })


        return retrofitClient.register(dto)
            .toFlowable()
    }

    fun recoverPass(email: String): Completable {
        val dto = RecoveryDTO(email)
        return retrofitClient.recovery(dto)
    }

    fun saveToken(token: String, cookies: String) {
        authPrefs.edit {
            putString(PREFS_TOKEN, token)
            putString(PREFS_COOKIES, cookies)
        }
    }

    fun saveFcmToken(token: String) {
        authPrefs.edit {
            putString(FCM_TOKEN, token)
        }
    }

    fun saveCookie(cookie: String) {
        authPrefs.edit {
            putString(PREFS_COOKIES, cookie)
        }
    }

    fun getCookie(): String {
        val cookie = authPrefs.getString(PREFS_COOKIES, "")
        if (cookie == null) {
            return  ""
        } else {
            return cookie
        }
    }

    fun clearCookie() {
        authPrefs.edit {
            remove(PREFS_COOKIES)
            remove(FCM_TOKEN)
        }
    }

}