package com.example.kabinetzhitelya_kotlin.data.repositories

import androidx.core.content.edit
import com.example.kabinetzhitelya_kotlin.data.PreferenseStorage
import com.example.kabinetzhitelya_kotlin.data.network.RetrofitClient
import com.example.kabinetzhitelya_kotlin.data.network.models.LoginUserDTO
import com.example.kabinetzhitelya_kotlin.data.network.models.RegisterUserDTO
import com.example.kabinetzhitelya_kotlin.data.network.models.SuccessAuthResponse
import io.reactivex.Flowable
import kotlinx.serialization.SerialName

object AuthRepository {

    private val retrofitClient = RetrofitClient.endpointsInterface
    private const val PREFS_TOKEN = "token"
    private const val PREFS_COOKIES = "cookies"
    private val authPrefs = PreferenseStorage.authPrefs

    fun login(username: String, password: String): Flowable<SuccessAuthResponse> {
        val dto = LoginUserDTO(username, password)
        return retrofitClient.login(dto)
            .toFlowable()
    }

    fun register(number: Int, lastName: String, email: String): Flowable<SuccessAuthResponse> {
        val fcmToken = ""
        val dto = RegisterUserDTO(number, lastName, email, fcmToken)
        return retrofitClient.register(dto)
            .toFlowable()
    }

    fun saveToken(token: String, cookies: String) {
        authPrefs.edit {
            putString(PREFS_TOKEN, token)
            putString(PREFS_COOKIES, cookies)
        }
    }

    fun saveCookie(cookie: String) {
        authPrefs.edit {
            putString(PREFS_COOKIES, cookie)
        }
    }

}