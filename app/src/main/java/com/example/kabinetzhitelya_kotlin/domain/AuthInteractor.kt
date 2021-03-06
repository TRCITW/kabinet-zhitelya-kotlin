package com.example.kabinetzhitelya_kotlin.domain

import com.example.kabinetzhitelya_kotlin.data.network.models.ErrorResponse
import com.example.kabinetzhitelya_kotlin.data.network.models.SuccessAuthResponse
import com.example.kabinetzhitelya_kotlin.data.repositories.AuthRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Call

class AuthInteractor {

    fun login(username: String, password: String): Flowable<SuccessAuthResponse> {
        return AuthRepository.login(username, password)
    }

    fun register(number: String, lastName: String, email: String): Flowable<ErrorResponse> {
        return AuthRepository.register(number, lastName, email)
    }

    fun recoveryPass(email: String): Completable {
        return AuthRepository.recoverPass(email)
    }

    fun getCookie(): String {
        return AuthRepository.getCookie()
    }

    fun clearCookie() {
        AuthRepository.clearCookie()
    }
}