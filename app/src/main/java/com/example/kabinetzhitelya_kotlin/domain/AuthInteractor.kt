package com.example.kabinetzhitelya_kotlin.domain

import com.example.kabinetzhitelya_kotlin.data.network.models.SuccessAuthResponse
import com.example.kabinetzhitelya_kotlin.data.repositories.AuthRepository
import io.reactivex.Flowable

class AuthInteractor {

    fun login(username: String, password: String): Flowable<SuccessAuthResponse> {
        return AuthRepository.login(username, password)
            .doOnNext {
                AuthRepository.saveToken(it.token, it.token)
            }
    }

    fun register(number: Int, lastName: String, email: String): Flowable<SuccessAuthResponse> {
        return AuthRepository.register(number, lastName, email)
            .doOnNext {
                AuthRepository.saveToken(it.token, it.token)
            }
    }
}